package com.example.licious.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licious.R;
import com.example.licious.listener.FavoriteListener;
import com.example.licious.response.AllWishListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder>{
    public static List<AllWishListResponse.Datum> ItemList;
    private Context context;
    FavoriteListener listener;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";

    public WishListAdapter(Context context, List<AllWishListResponse.Datum> ItemList,FavoriteListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_wishlist, parent, false);
        WishListAdapter.ViewHolder viewHolder = new WishListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder holder, int position) {
        holder.tv_productTitle.setText(ItemList.get(position).getProduct_title());
        holder.tv_weight.setText(ItemList.get(position).getWeight() + ItemList.get(position).getWeight_type());
        holder.discount_price.setText(ItemList.get(position).getPrice());
        holder.tv_basePrice.setText(ItemList.get(position).getMrp());
        Picasso.with(context)
                .load(image_url+ItemList.get(position).getProduct_image())
                .into(holder.iv_images);

    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_productTitle,tv_weight,discount_price,tv_basePrice;
        CircleImageView iv_images;
        LinearLayout btn_addTocart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_productTitle = (TextView) itemView.findViewById(R.id.tv_productTitle);
            tv_weight = (TextView) itemView.findViewById(R.id.tv_weight);
            discount_price = (TextView) itemView.findViewById(R.id.discount_price);
            tv_basePrice = (TextView) itemView.findViewById(R.id.tv_basePrice);
            iv_images = (CircleImageView) itemView.findViewById(R.id.iv_images);
            btn_addTocart = (LinearLayout) itemView.findViewById(R.id.btn_addTocart);

            btn_addTocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickedAdd(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });

        }
    }
}
