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
import com.example.licious.listener.SearchItemClickListener;
import com.example.licious.listener.SubCategoriesProductListener;
import com.example.licious.response.SearchResponse;
import com.example.licious.response.SubCategoryItemResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SeacrhItemListAdapter extends RecyclerView.Adapter<SeacrhItemListAdapter.ViewHolder>{
    public static List<SearchResponse.Datum> ItemList;
    private Context context;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";
    SearchItemClickListener listener;

    public SeacrhItemListAdapter(Context context, List<SearchResponse.Datum> ItemList,SearchItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SeacrhItemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search_data, parent, false);
        SeacrhItemListAdapter.ViewHolder viewHolder = new SeacrhItemListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SeacrhItemListAdapter.ViewHolder holder, int position) {
        holder.tv_tittle_prods.setText(ItemList.get(position).getProductTitle());
        holder.tv_weight.setText(ItemList.get(position).getWeight() + ItemList.get(position).getWeightType());
        holder.tv_price.setText(ItemList.get(position).getPrice());
        holder.basePrice.setText(ItemList.get(position).getMrp());

        Picasso.with(context)
                .load(image_url+ItemList.get(position).getProductImage())
                .into(holder.iv_icon);
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView iv_icon;
        TextView tv_tittle_prods,tv_weight,tv_price,basePrice;
        LinearLayout ll_add;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = (CircleImageView) itemView.findViewById(R.id.iv_icon);
            tv_tittle_prods = (TextView) itemView.findViewById(R.id.tv_tittle_prods);
            tv_weight = (TextView) itemView.findViewById(R.id.tv_weight);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            basePrice = (TextView) itemView.findViewById(R.id.basePrice);
            ll_add = (LinearLayout) itemView.findViewById(R.id.ll_add);


            ll_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickedSearchItem(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });
        }
    }
}
