package com.example.licious.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licious.R;
import com.example.licious.fragment.response.AllAddressListResponse;

import java.util.List;


public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {
    public static List<AllAddressListResponse.Datum> ItemList;
    private Context context;

    private final AddressListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(AllAddressListResponse.Datum item, int position, int type);
        void onItemClickEdit(AllAddressListResponse.Datum item, int position, int type);
    }

    public AddressListAdapter(Context context, List<AllAddressListResponse.Datum> ItemList,AddressListAdapter.OnItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddressListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_address, parent, false);
        AddressListAdapter.ViewHolder viewHolder = new AddressListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressListAdapter.ViewHolder holder, int position) {
        holder.address_type.setText(ItemList.get(position).getAddress_type());
        holder.tv_addressLine1.setText(ItemList.get(position).getAddress_line_one());
        holder.tv_addressLine2.setText(ItemList.get(position).getAddress_line_two());
        holder.tv_city.setText(ItemList.get(position).getCity());
        holder.tv_phone.setText(ItemList.get(position).getMobile_number());
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address_type, tv_addressLine1, tv_addressLine2, tv_city, tv_phone, tv_delete,tv_edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address_type = (TextView) itemView.findViewById(R.id.address_type);
            tv_addressLine1 = (TextView) itemView.findViewById(R.id.tv_addressLine1);
            tv_addressLine2 = (TextView) itemView.findViewById(R.id.tv_addressLine2);
            tv_city = (TextView) itemView.findViewById(R.id.tv_city);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
            tv_edit = (TextView) itemView.findViewById(R.id.tv_edit);

            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });

            tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickEdit(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });

        }
    }
}
