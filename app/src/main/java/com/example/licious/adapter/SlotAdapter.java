package com.example.licious.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licious.R;
import com.example.licious.listener.SlotListener;
import com.example.licious.response.SlotResponse;

import java.util.List;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.ViewHolder> {
    public static List<SlotResponse.Datum> ItemList;
    private Context context;
    Boolean flag=false;
    private int selectedPosition = -1;
    SlotListener listener;

    public SlotAdapter(Context context, List<SlotResponse.Datum> ItemList,Boolean flag, SlotListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.flag = flag;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SlotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_slot, parent, false);
        SlotAdapter.ViewHolder viewHolder = new SlotAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SlotAdapter.ViewHolder holder, int position) {
        holder.txt_slot.setText(ItemList.get(position).getSlot_name());

    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_slot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_slot = (TextView) itemView.findViewById(R.id.txt_slots);

            txt_slot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!flag) {
                        txt_slot.setBackgroundResource((R.drawable.slotselected_bg));
                        flag = true;
                        listener.onItemClickedSlot(ItemList.get(getAdapterPosition()), getAdapterPosition(), flag);
                    } else {
                        txt_slot.setBackgroundResource((R.drawable.time_bg));
                        flag = false;
                        listener.onItemClickedSlot(ItemList.get(getAdapterPosition()), getAdapterPosition(), flag);
                    }
                }
            });
        }
    }
}
