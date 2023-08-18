package com.example.licious.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licious.R;
import com.example.licious.response.AllAddressListResponse;
import com.example.licious.response.OrderHistoryDataResponse;
import com.example.licious.response.OrderHistoryResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>{
    public static List<OrderHistoryDataResponse.Datum> ItemList;
    private Context context;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";

    public OrderHistoryAdapter(Context context, List<OrderHistoryDataResponse.Datum> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_order_history, parent, false);
        OrderHistoryAdapter.ViewHolder viewHolder = new OrderHistoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.ViewHolder holder, int position) {
        holder.tv_title.setText(ItemList.get(position).getProductTitle());
        holder.tv_weight.setText(ItemList.get(position).getWeight() + ItemList.get(position).getWeightType());
        holder.tv_discountPrice.setText(ItemList.get(position).getPrice());
        holder.tv_basePrice.setText(ItemList.get(position).getMrp());
        holder.tv_qnt.setText(ItemList.get(position).getQty() + " " + "Qnt");

        Picasso.with(context)
                .load(image_url+ItemList.get(position).getProductImage())
                .into(holder.iv_image);
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_weight,tv_discountPrice,tv_basePrice,tv_qnt;
        CircleImageView iv_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_weight = (TextView) itemView.findViewById(R.id.tv_weight);
            tv_discountPrice = (TextView) itemView.findViewById(R.id.tv_discountPrice);
            tv_basePrice = (TextView) itemView.findViewById(R.id.tv_basePrice);
            tv_qnt = (TextView) itemView.findViewById(R.id.tv_qnt);
            iv_image = (CircleImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
