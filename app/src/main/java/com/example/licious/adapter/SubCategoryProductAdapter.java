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
import com.example.licious.listener.SubCategoriesProductListener;
import com.example.licious.response.GetMasterCategoryResponse;
import com.example.licious.response.SubCategoryItemResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubCategoryProductAdapter extends RecyclerView.Adapter<SubCategoryProductAdapter.ViewHolder>{
    public static List<SubCategoryItemResponse.Datum> ItemList;
    private Context context;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";
    SubCategoriesProductListener listener;

    public SubCategoryProductAdapter(Context context, List<SubCategoryItemResponse.Datum> ItemList,SubCategoriesProductListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener= listener;
    }

    @NonNull
    @Override
    public SubCategoryProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_product, parent, false);
        SubCategoryProductAdapter.ViewHolder viewHolder = new SubCategoryProductAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryProductAdapter.ViewHolder holder, int position) {
        holder.pd_title.setText(ItemList.get(position).getProduct_title());
        holder.tv_pd_des.setText(ItemList.get(position).getShort_description());
        holder.tv_weight.setText(ItemList.get(position).getWeight() + ItemList.get(position).getWeight_type());
        holder.tv_pieces.setText(ItemList.get(position).getPieces());
        holder.tv_serves.setText(ItemList.get(position).getServes());
        holder.prices.setText(ItemList.get(position).getPrice());
        holder.tv_basePrices.setText(ItemList.get(position).getMrp());
        holder.tv_offer.setText(ItemList.get(position).getOffer());

        Picasso.with(context)
                .load(image_url+ItemList.get(position).getProduct_image())
                .into(holder.iv_bg);

    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_bg,IV_wishList;
        TextView pd_title,tv_pd_des,tv_weight,tv_pieces,tv_serves,prices,tv_basePrices,tv_offer;
        LinearLayout ll_add;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_bg = (ImageView) itemView.findViewById(R.id.iv_bg);
            pd_title = (TextView) itemView.findViewById(R.id.pd_title);
            tv_pd_des = (TextView) itemView.findViewById(R.id.tv_pd_des);
            tv_weight = (TextView) itemView.findViewById(R.id.tv_weight);
            tv_pieces = (TextView) itemView.findViewById(R.id.tv_pieces);
            tv_serves = (TextView) itemView.findViewById(R.id.tv_serves);
            prices = (TextView) itemView.findViewById(R.id.prices);
            tv_basePrices = (TextView) itemView.findViewById(R.id.tv_basePrices);
            tv_offer = (TextView) itemView.findViewById(R.id.tv_offer);
            ll_add = (LinearLayout) itemView.findViewById(R.id.ll_add);
            IV_wishList = (ImageView) itemView.findViewById(R.id.IV_wishList);

            ll_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickedCategoriesProduct(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });

            IV_wishList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickedCategoriesProductWishList(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });

        }
    }
}