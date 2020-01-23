package my.edu.unikl.placeitright;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartImageAdapter extends RecyclerView.Adapter<CartImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<CartInfo> mCartInfo;
    private FragmentManager fragmentManager;

    public CartImageAdapter() {
    }

    public CartImageAdapter(Context mContext, List<CartInfo> mCartInfo, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.mCartInfo = mCartInfo;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public CartImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cart_recyclerview_item, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartImageAdapter.ImageViewHolder imageViewHolder, final int position) {
        final CartInfo cartInfoCurrent = mCartInfo.get(position);
        imageViewHolder.txtProductName.setText(cartInfoCurrent.getProductName());
        imageViewHolder.txtProductPrice.setText("RM " +cartInfoCurrent.getProductPrice());
        imageViewHolder.editTextQuantity.setText(cartInfoCurrent.getQuantity());

        //initialize each productQuantity
        int currQuantity = Integer.parseInt(cartInfoCurrent.getQuantity());

        //buttonOnClickListener
        imageViewHolder.minusQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tempQuantity = currQuantity - 1;
                if(tempQuantity<1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                    builder.setTitle("Delete Product");
                    builder.setMessage("Are you sure you want to delete this item?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) { cartInfoCurrent.deleteItem();
                            dialog.dismiss(); }});
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { dialog.dismiss(); }});

                    AlertDialog alert = builder.create();
                    alert.show();
                }else{
                    String newQuantity = String.valueOf(tempQuantity);
                    cartInfoCurrent.setQuantity(newQuantity);
                    cartInfoCurrent.updateCartDatabase();
                }
            }
        });
        imageViewHolder.plusQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tempQuantity = currQuantity + 1;
                String newQuantity = String.valueOf(tempQuantity);
                cartInfoCurrent.setQuantity(newQuantity);
                cartInfoCurrent.updateCartDatabase();
            }
        });
        imageViewHolder.DeleteItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle("Delete Product");
                builder.setMessage("Are you sure you want to delete this item?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { cartInfoCurrent.deleteItem();
                        dialog.dismiss(); }});
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { dialog.dismiss(); }});

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        Picasso.get()
                .load(cartInfoCurrent.getProductImage())
                .placeholder(R.mipmap.ic_launcher)
                .fit().centerCrop().into(imageViewHolder.imgProductImage);

        imageViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                ProductDetailsFragment fragment = new ProductDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putString("productID", cartInfoCurrent.getProductId());
                bundle.putString("productDetails", cartInfoCurrent.getProductDetail());
                bundle.putString("tempUrl", cartInfoCurrent.getProductImgUrls());

                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCartInfo.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout parentLayout;
        public TextView txtProductName;
        public TextView txtProductPrice;
        public ImageView imgProductImage;
        public EditText editTextQuantity;
        Button minusQuantityBtn, plusQuantityBtn, DeleteItemBtn;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.cartRecyclerViewLayout);
            txtProductName = (TextView) itemView.findViewById(R.id.cartProductName);
            txtProductPrice = (TextView) itemView.findViewById(R.id.cartProductPrice);
            imgProductImage = (ImageView) itemView.findViewById(R.id.cartProductView);
            editTextQuantity = (EditText) itemView.findViewById(R.id.editTextQuantity);
            minusQuantityBtn = (Button) itemView.findViewById(R.id.MinusQuantityBtn);
            plusQuantityBtn = (Button) itemView.findViewById(R.id.PlusQuantityBtn);
            DeleteItemBtn = (Button) itemView.findViewById(R.id.DeleteItemBtn);
        }


    }

    public interface productFragmentListener{
        void onInputSent(String input);
    }

}
