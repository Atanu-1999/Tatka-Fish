package com.example.licious.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licious.R;
import com.example.licious.listener.TopSellerListener;
import com.example.licious.activity.ProductDetails;
import com.example.licious.response.Best_Seller_Response;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class Top_Rated_Adapter extends RecyclerView.Adapter<Top_Rated_Adapter.ViewHolder>{
    public static List<Best_Seller_Response.Datum> ItemList;
    private Context context;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";
    private TopSellerListener listener;
    Boolean status;

    public Top_Rated_Adapter(Context context, List<Best_Seller_Response.Datum> ItemList,Boolean status,TopSellerListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
        this.status = status;
    }
    @NonNull
    @Override
    public Top_Rated_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bestseller, parent, false);
        Top_Rated_Adapter.ViewHolder viewHolder = new Top_Rated_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Top_Rated_Adapter.ViewHolder holder, int position) {
        holder.tv_title.setText(ItemList.get(position).getProduct_title());
        holder.tv_weight.setText(ItemList.get(position).getWeight() + " " + ItemList.get(position).getWeight_type());
        holder.tv_mrp.setText("₹" + ItemList.get(position).getPrice());
        holder.tv_basePrice.setText("₹" + ItemList.get(position).getMrp());
        holder.tv_discount.setText(ItemList.get(position).getOffer() + "% off");
        Picasso.with(context)
                .load(image_url+ItemList.get(position).getProduct_image())
                .into(holder.iv_bestSeller);

        if (Objects.equals(ItemList.get(position).getWishlist_status(), "False"))
        {
            holder.fav_image.setImageResource(R.drawable.baseline_favorite_border_24);
        }
        else {
            holder.fav_image.setImageResource(R.drawable.baseline_favorite_24);
        }

    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_bestSeller,fav_image,btn_add;
        TextView tv_title,tv_weight,tv_mrp,tv_discount,tv_basePrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_bestSeller =  (ImageView) itemView.findViewById(R.id.iv_bestSeller);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_weight = (TextView) itemView.findViewById(R.id.tv_weight);
            tv_mrp = (TextView) itemView.findViewById(R.id.tv_mrp);
            tv_discount =  (TextView) itemView.findViewById(R.id.tv_discount);
            fav_image = (ImageView) itemView.findViewById(R.id.fav_image);
            btn_add = (ImageView) itemView.findViewById(R.id.btn_add);
            tv_basePrice = (TextView) itemView.findViewById(R.id.tv_base_Price);

            fav_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  listener.onItemClick(ItemList.get(getAdapterPosition()), getAdapterPosition());
                    listener.onItemClickedWishList(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1,status);
                }
            });
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  listener.onItemClick(ItemList.get(getAdapterPosition()), getAdapterPosition());
                    listener.onItemClickedAdd(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  listener.onItemClick(ItemList.get(getAdapterPosition()), getAdapterPosition());
                    listener.onItemClickedItem(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });

        }
    }
}
