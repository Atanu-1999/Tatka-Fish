package com.example.licious.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licious.R;
import com.example.licious.listener.OrderHistoryListener;
import com.example.licious.response.HistoryResponse;

import java.util.List;

public class OrderHistoryDataAdapter extends RecyclerView.Adapter<OrderHistoryDataAdapter.ViewHolder>{
    public static List<HistoryResponse.Datum> ItemList;
    private Context context;
    OrderHistoryListener listener;

    public OrderHistoryDataAdapter(Context context, List<HistoryResponse.Datum> ItemList,OrderHistoryListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderHistoryDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_history_order, parent, false);
        OrderHistoryDataAdapter.ViewHolder viewHolder = new OrderHistoryDataAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryDataAdapter.ViewHolder holder, int position) {
        holder.tv_date.setText(ItemList.get(position).getOrderDate());
        holder.tv_orderId.setText(ItemList.get(position).getOrderNo());
        holder.tv_status.setText(ItemList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date,tv_orderId,tv_status;
        CardView cv_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_orderId = (TextView) itemView.findViewById(R.id.tv_orderId);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            cv_item = (CardView) itemView.findViewById(R.id.cv_item);

            cv_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickedOrderHistory(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });
        }
    }
}
