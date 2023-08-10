package com.example.licious.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licious.listener.DeleteListener;
import com.example.licious.R;
import com.example.licious.response.CartDetailsResponse;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder>{
    public static List<CartDetailsResponse.Datum> ItemList;
    private Context context;
    private DeleteListener listener;

    public MyCartAdapter(Context context, List<CartDetailsResponse.Datum> ItemList,DeleteListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart, parent, false);
        MyCartAdapter.ViewHolder viewHolder = new MyCartAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {
        holder.tv_produc_tTitle.setText(ItemList.get(position).getProduct_title());
        holder.tv_weight.setText(ItemList.get(position).getWeight() + " " + ItemList.get(position).getWeight_type());
        holder.tv_price.setText("₹" + ItemList.get(position).getPrice());
        holder.tv_base_Price.setText(ItemList.get(position).getMrp());
        holder.tv_qty.setText(ItemList.get(position).getQty());
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_produc_tTitle,tv_weight,tv_price,tv_base_Price,tv_qty;
        ImageView iv_add,iv_Sub,iv_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_add =  (ImageView) itemView.findViewById(R.id.iv_add);
            iv_Sub =  (ImageView) itemView.findViewById(R.id.iv_Sub);
            tv_produc_tTitle = (TextView) itemView.findViewById(R.id.tv_produc_tTitle);
            tv_weight = (TextView) itemView.findViewById(R.id.tv_weight);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_base_Price = (TextView) itemView.findViewById(R.id.tv_base_Price);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            tv_qty = (TextView) itemView.findViewById(R.id.tv_qty);

            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickedDelete(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });
            iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  listener.onItemClick(ItemList.get(getAdapterPosition()), getAdapterPosition());
                    listener.onItemClickedAdditem(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });
            iv_Sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  listener.onItemClick(ItemList.get(getAdapterPosition()), getAdapterPosition());
                    listener.onItemClickedRemoveItem(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });
        }
    }
}
