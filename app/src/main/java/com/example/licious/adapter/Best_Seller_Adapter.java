package com.example.licious.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licious.listener.BestSellerListener;
import com.example.licious.R;
import com.example.licious.activity.ProductDetails;
import com.example.licious.response.Best_Seller_Response;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Best_Seller_Adapter extends RecyclerView.Adapter<Best_Seller_Adapter.ViewHolder> {
    public static List<Best_Seller_Response.Datum> ItemList;
    private Context context;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";
    private BestSellerListener listener;
    String BlankId = "";
    Boolean status;


//    public interface OnItemClickListener {
//        void onItemClick(Best_Seller_Response.Datum item, int position);
//    }

    public Best_Seller_Adapter(Context context, List<Best_Seller_Response.Datum> ItemList,Boolean status, BestSellerListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
        this.status = status;
    }

    @NonNull
    @Override
    public Best_Seller_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bestseller, parent, false);
        Best_Seller_Adapter.ViewHolder viewHolder = new Best_Seller_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Best_Seller_Adapter.ViewHolder holder, int position) {

        holder.tv_title.setText(ItemList.get(position).getProduct_title());
        holder.tv_weight.setText(ItemList.get(position).getWeight() + " " + ItemList.get(position).getWeight_type());
        holder.tv_mrp.setText("₹" + ItemList.get(position).getPrice());
        holder.tv_basePrice.setText("₹" + ItemList.get(position).getMrp());
        holder.tv_discount.setText(ItemList.get(position).getOffer() + "% off");
        Picasso.with(context)
                .load(image_url+ItemList.get(position).getProduct_image())
                .into(holder.iv_bestSeller);

        if (status)
        {
            holder.fav_image.setImageResource(R.drawable.baseline_favorite_border_24);
        }
        else {
            holder.fav_image.setImageResource(R.drawable.baseline_favorite_24);
        }

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////               Intent i = new Intent(context, ProductDetails.class);
////               context.startActivity(i);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_bestSeller,btn_add,fav_image;
        TextView tv_title,tv_weight,tv_mrp,tv_discount,tv_basePrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_bestSeller =  (ImageView) itemView.findViewById(R.id.iv_bestSeller);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_weight = (TextView) itemView.findViewById(R.id.tv_weight);
            tv_mrp = (TextView) itemView.findViewById(R.id.tv_mrp);
            tv_discount =  (TextView) itemView.findViewById(R.id.tv_discount);
            btn_add = (ImageView) itemView.findViewById(R.id.btn_add);
            fav_image = (ImageView) itemView.findViewById(R.id.fav_image);
            tv_basePrice = (TextView) itemView.findViewById(R.id.tv_base_Price);

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  listener.onItemClick(ItemList.get(getAdapterPosition()), getAdapterPosition());
                    listener.onItemClickedmy(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });
            fav_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  listener.onItemClick(ItemList.get(getAdapterPosition()), getAdapterPosition());
                    listener.onItemClickedWishList(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1,status);
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
