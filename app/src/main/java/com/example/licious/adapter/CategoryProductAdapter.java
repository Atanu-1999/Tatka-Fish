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
import com.example.licious.listener.MasterCategoryprouduct;
import com.example.licious.response.Category_Response;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.ViewHolder>{
    public static List<Category_Response.Datum> ItemList;
    private Context context;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";
    MasterCategoryprouduct listener;

    public CategoryProductAdapter(Context context, List<Category_Response.Datum> ItemList, MasterCategoryprouduct listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_product, parent, false);
        CategoryProductAdapter.ViewHolder viewHolder = new CategoryProductAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductAdapter.ViewHolder holder, int position) {
        holder.pd_title.setText(ItemList.get(position).getProduct_title());
        holder.tv_pd_des.setText(ItemList.get(position).getShort_description());
        holder.tv_weight.setText(ItemList.get(position).getWeight() + ItemList.get(position).getWeight_type());
        holder.tv_pieces.setText(ItemList.get(position).getPieces() + " " + "Pieces");
        holder.tv_serves.setText(ItemList.get(position).getServes() + " " + "Serves");
        holder.prices.setText("₹" + ItemList.get(position).getPrice());
        holder.tv_basePrices.setText(ItemList.get(position).getMrp());
        holder.tv_offer.setText(ItemList.get(position).getOffer() + "% off");

        if (ItemList.get(position).getProduct_image() == null) {
            Picasso.with(context)
                    .load(R.drawable.noimagesqur)
                    .into(holder.iv_bg);
        }else {
            Picasso.with(context)
                    .load(image_url + ItemList.get(position).getProduct_image())
                    .into(holder.iv_bg);
        }

        if (Objects.equals(ItemList.get(position).getWishlist_status(), "False"))
        {
            holder.IV_wishList.setImageResource(R.drawable.baseline_favorite_border_24);
        }
        else {
            holder.IV_wishList.setImageResource(R.drawable.baseline_favorite_24);
        }
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_bg,IV_wishList;
        TextView pd_title,tv_pd_des,tv_weight,tv_pieces,tv_serves,prices,tv_basePrices,tv_offer;
        LinearLayout ll_add,all_btn;
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
            all_btn = (LinearLayout) itemView.findViewById(R.id.all_btn);

            ll_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickedMasterCategoriesProductADDcart(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });

            IV_wishList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickedMasterCategoriesProductWishList(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });
            all_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickedItem(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });

        }
    }
}
