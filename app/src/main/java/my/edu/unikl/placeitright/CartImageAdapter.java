package my.edu.unikl.placeitright;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<ProductInfo> mProductInfo;
    private FragmentManager fragmentManager;

    public ProductImageAdapter(Context context, List<ProductInfo> productInfo, FragmentManager fragmentManager){
        this.mContext = context;
        this.mProductInfo  = productInfo;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ProductImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.product_recyclerview_item, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductImageAdapter.ImageViewHolder imageViewHolder, final int position) {
        final ProductInfo productInfoCurrent = mProductInfo.get(position);
        imageViewHolder.txtProductName.setText(productInfoCurrent.getProductName());
        imageViewHolder.txtProductPrice.setText(productInfoCurrent.getProductPrice());
        Picasso.get()
                .load(productInfoCurrent.getProductImage())
                .placeholder(R.mipmap.ic_launcher)
                .fit().centerCrop().into(imageViewHolder.imgProductImage);

        imageViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                ProductDetailsFragment fragment = new ProductDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putString("productID", productInfoCurrent.getProductId());
                bundle.putString("productDetails", productInfoCurrent.getProductDetails());
                bundle.putString("tempUrl", productInfoCurrent.getProductImageUrl());

                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductInfo.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout parentLayout;
        public TextView txtProductName;
        public TextView txtProductPrice;
        public ImageView imgProductImage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.recyclerViewLayout);
            txtProductName = itemView.findViewById(R.id.ProductName1);
            txtProductPrice = itemView.findViewById(R.id.ProductPrice1);
            imgProductImage = itemView.findViewById(R.id.ProductView1);

        }
    }

    public interface productFragmentListener{
        void onInputSent(String input);
    }

}
