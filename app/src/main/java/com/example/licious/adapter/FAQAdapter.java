package com.example.licious.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licious.R;
import com.example.licious.listener.NotificationListener;
import com.example.licious.response.FaqResponse;
import com.example.licious.response.NotificationListResponse;

import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder>{
    public static List<FaqResponse.Datum> ItemList;
    private Context context;
    Boolean flag = false;

    public FAQAdapter(Context context, List<FaqResponse.Datum> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public FAQAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faqlist, parent, false);
        FAQAdapter.ViewHolder viewHolder = new FAQAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FAQAdapter.ViewHolder holder, int position) {
        holder.title.setText(ItemList.get(position).getTitle());
        holder.des.setText(ItemList.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    holder.des.setVisibility(View.VISIBLE);
                    holder.add.setVisibility(View.GONE);
                    holder.sub.setVisibility(View.VISIBLE);
                    flag=true;
                }
                else {
                    holder.des.setVisibility(View.GONE);
                    holder.add.setVisibility(View.VISIBLE);
                    holder.sub.setVisibility(View.GONE);
                    flag=false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,des;
        ImageView add,sub;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            des = (TextView) itemView.findViewById(R.id.des);
            add = (ImageView) itemView.findViewById(R.id.add);
            sub = (ImageView) itemView.findViewById(R.id.sub);
        }
    }
}
