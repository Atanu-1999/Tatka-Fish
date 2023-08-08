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
import com.example.licious.activity.ProductDetails;
import com.example.licious.fragment.response.Best_Seller_Response;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Top_Rated_Adapter extends RecyclerView.Adapter<Top_Rated_Adapter.ViewHolder>{
    public static List<Best_Seller_Response.Datum> ItemList;
    private Context context;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";

    public Top_Rated_Adapter(Context context, List<Best_Seller_Response.Datum> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
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
        holder.tv_mrp.setText("₹" + ItemList.get(position).getMrp());
        holder.tv_discount.setText(ItemList.get(position).getOffer() + "% off");
        Picasso.with(context)
                .load(image_url+ItemList.get(position).getProduct_image())
                .into(holder.iv_bestSeller);

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProductDetails.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_bestSeller,fav_image,btn_add;
        TextView tv_title,tv_weight,tv_mrp,tv_discount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_bestSeller =  (ImageView) itemView.findViewById(R.id.iv_bestSeller);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_weight = (TextView) itemView.findViewById(R.id.tv_weight);
            tv_mrp = (TextView) itemView.findViewById(R.id.tv_mrp);
            tv_discount =  (TextView) itemView.findViewById(R.id.tv_discount);
            fav_image = (ImageView) itemView.findViewById(R.id.fav_image);
            btn_add = (ImageView) itemView.findViewById(R.id.btn_add);

        }
    }
}
