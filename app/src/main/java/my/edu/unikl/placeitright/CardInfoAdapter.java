package my.edu.unikl.placeitright;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class CheckOutImageAdapter extends RecyclerView.Adapter<CheckOutImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<CartInfo> mCartInfo;
    private String TAG = "ChckOutImgAdapter:";

    public CheckOutImageAdapter() {
    }

    public CheckOutImageAdapter(Context mContext, List<CartInfo> mCartInfo) {
        this.mContext = mContext;
        this.mCartInfo = mCartInfo;
    }

    @NonNull
    @Override
    public CheckOutImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.checkout_recyclerview_item, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutImageAdapter.ImageViewHolder imageViewHolder, final int position) {
        final CartInfo cartInfoCurrent = mCartInfo.get(position);
        imageViewHolder.txtProductName.setText(cartInfoCurrent.getProductName());
        imageViewHolder.txtProductPrice.setText("RM " +cartInfoCurrent.getProductPrice());
        imageViewHolder.editTextQuantity.setText(cartInfoCurrent.getQuantity());

        Picasso.get()
                .load(cartInfoCurrent.getProductImage())
                .placeholder(R.mipmap.ic_launcher)
                .fit().centerCrop().into(imageViewHolder.imgProductImage);
    }

    @Override
    public int getItemCount() {
        return mCartInfo.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout parentLayout;
        public TextView txtProductName, txtProductPrice, editTextQuantity;
        public ImageView imgProductImage;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.checkOutRecyclerViewLayout);
            txtProductName = (TextView) itemView.findViewById(R.id.chkOutProductName);
            txtProductPrice = (TextView) itemView.findViewById(R.id.chkOutProductPrice);
            imgProductImage = (ImageView) itemView.findViewById(R.id.chkOutProductView);
            editTextQuantity = (TextView) itemView.findViewById(R.id.chkOuTeditTxtQuantity);
        }


    }


}
