package my.edu.unikl.placeitright;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.RotationController;
import com.google.ar.sceneform.ux.TransformableNode;

import static my.edu.unikl.placeitright.ARviewFragment.checkIsSupportedDeviceOrFinish;

public class ShowroomARFragment extends AppCompatActivity{
        private static final String TAG = ARviewFragment.class.getSimpleName();
        private static final double MIN_OPENGL_VERSION = 3.0;
        public String productModel;
        public int tapCount=0, clickCount = 10;

        private ArFragment arFragment;
        private ModelRenderable productRenderable;
        Button addProductBtn;

        public ImageView imageView;

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
        // CompletableFuture requires api level 24
        // FutureReturnValueIgnored is not valid
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (!checkIsSupportedDeviceOrFinish(this)) {
                return;
            }
            setContentView(R.layout.fragment_showroom_ar);
            productModel = "https://firebasestorage.googleapis.com/v0/b/placeitright-32ae2.appspot.com/o/productModel%2FBaby%26Children%2FCots%2FGonnat%2Fgonnat.sfb?alt=media&token=b8b8b2ee-137d-4663-9f9d-ca4c38faad88";
            addProductBtn = (Button) findViewById(R.id.addproductIcon);
            if(clickCount>0){
                Toast.makeText(getApplicationContext(),"You can add up to " +clickCount +" product!", Toast.LENGTH_SHORT).show();
                addProductBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addProduct();
                        clickCount--;
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(), "You can only add up to 10 product!", Toast.LENGTH_SHORT).show();
            }
        }

        public void addProduct(){
            startActivity(new Intent(getApplicationContext(),MixAndMatchActivity.class));
        }

        @SuppressLint("NewApi")
        public void getProduct(String productLink){
            productModel = productLink;
            arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_showroomfragment);

            // When you build a Renderable, Sceneform loads its resources in the background while returning
            // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
            ModelRenderable.builder()
                    .setSource(this, Uri.parse(productModel))
                    .build()
                    .thenAccept(renderable -> productRenderable = renderable)
                    .exceptionally(
                            throwable -> {
                                Toast toast =
                                        Toast.makeText(this, "Unable to load product renderable", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return null;
                            });

            arFragment.setAllowEnterTransitionOverlap(false);
            Toast.makeText(getApplicationContext(),"Tap anywhere to place the model!", Toast.LENGTH_SHORT).show();
            if(tapCount<1) {
                arFragment.setOnTapArPlaneListener(
                        (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                            if (productRenderable == null) {
                                return;
                            }

                            // Create the Anchor.
                            Anchor anchor = hitResult.createAnchor();
                            AnchorNode anchorNode = new AnchorNode(anchor);
                            //anchorNode.isSmoothed();
                            anchorNode.setParent(arFragment.getArSceneView().getScene());

                            // Create the transformable product and add it to the anchor.
                            TransformableNode product = new TransformableNode(arFragment.getTransformationSystem());
                            product.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), -90f));
                            product.setOnTouchListener(new Node.OnTouchListener() {
                                @Override
                                public boolean onTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
                                    return false;
                                }
                            });
                            product.setParent(anchorNode);
                            product.setRenderable(productRenderable);
                            product.select();
                        });
                tapCount ++;
            }else{
                Toast.makeText(getApplicationContext(),"Please click the add button to add more product!", Toast.LENGTH_SHORT).show();
                tapCount = 0;
            }
        }

        public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                Log.e(TAG, "Sceneform requires Android N or later");
                Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
                activity.finish();
                return false;
            }
            String openGlVersionString =
                    ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                            .getDeviceConfigurationInfo()
                            .getGlEsVersion();
            if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
                Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
                Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                        .show();
                activity.finish();
                return false;
            }
            return true;
        }
}

