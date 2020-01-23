package my.edu.unikl.placeitright;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private DatabaseReference productDB, dbRef;
    TextView textViewName, textViewPrice, textViewDesc;
    ImageView imgView;
    private String productId;

    ExpandableListView expandableListView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        viewPager = (ViewPager) findViewById(R.id.ImageViewPager);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        textViewName = (TextView) findViewById(R.id.ViewName);
        textViewPrice = (TextView) findViewById(R.id.ViewPrice);
        textViewDesc = (TextView) findViewById(R.id.ViewDesc);
        imgView = (ImageView) findViewById(R.id.imageSlideView);
        getIncomingIntent();
        productDB = dbRef.child(productId);
        final List<String> tempUrl = null;

        productDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ProductDetailInfo mproductDetailInfo = new ProductDetailInfo();
                mproductDetailInfo.setProductName((String) dataSnapshot.child("productName").getValue());
                mproductDetailInfo.setProductPrice((String) dataSnapshot.child("productPrice").getValue());
                mproductDetailInfo.setProductInfo((String) dataSnapshot.child("productInfo").getValue());
                mproductDetailInfo.setProductId(dataSnapshot.getKey());
                mproductDetailInfo.setProductGoodToKnow((String) dataSnapshot.child("productGoodToKnow").getValue());
                mproductDetailInfo.setProductDesc((String) dataSnapshot.child("productDesc").getValue());
                mproductDetailInfo.setProductMatAndEnv((String) dataSnapshot.child("productMatAndEnv").getValue());
                mproductDetailInfo.setProductPakageDetail((String) dataSnapshot.child("productPakageDetail").getValue());
                for(DataSnapshot snap : dataSnapshot.child("imageURL").getChildren()){
                    String url = snap.getValue(String.class);
                    tempUrl.add(url);
                }
                mproductDetailInfo.setImageURL(tempUrl);
                textViewName.setText(mproductDetailInfo.getProductName());
                textViewDesc.setText(mproductDetailInfo.getProductDesc());
                textViewPrice.setText("RM " +mproductDetailInfo.getProductPrice());
                ViewPagerAdapter vpager = new ViewPagerAdapter(ProductDetailsActivity.this, mproductDetailInfo.getImageURL(),imgView);
                viewPager.setAdapter(vpager);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ExpandableListViewAdapter expViewAdptr = new ExpandableListViewAdapter(ProductDetailsActivity.this);

        expandableListView.setAdapter(expViewAdptr);
    }

    public ProductDetailsActivity() {
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("productId") && getIntent().hasExtra("dbRef") ){
            productId  = getIntent().getStringExtra("productId");
            String refURL = getIntent().getStringExtra("dbRef");
            dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl(refURL);
        }
    }
}
