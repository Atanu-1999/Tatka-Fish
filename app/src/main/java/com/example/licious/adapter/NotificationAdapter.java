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
import com.example.licious.listener.NotificationListener;
import com.example.licious.response.NotificationListResponse;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    public static List<NotificationListResponse.Datum> ItemList;
    private Context context;
    NotificationListener listener;

    public NotificationAdapter(Context context, List<NotificationListResponse.Datum> ItemList,NotificationListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notification, parent, false);
        NotificationAdapter.ViewHolder viewHolder = new NotificationAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.tv_date.setText(ItemList.get(position).getSendDate());
        holder.tv_orderId.setText(ItemList.get(position).getNotiTitle());
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date,tv_orderId;
        CardView cv_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_orderId = (TextView) itemView.findViewById(R.id.tv_orderId);
            cv_item = (CardView) itemView.findViewById(R.id.cv_item);

            cv_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });
        }
    }
}
