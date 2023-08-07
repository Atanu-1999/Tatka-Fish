package com.example.licious.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licious.BestSellerListener;
import com.example.licious.R;
import com.example.licious.response.AllAddressListResponse;
import com.example.licious.response.Best_Seller_Response;

import java.util.List;


public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder>{
    public static List<AllAddressListResponse.Datum> ItemList;
    private Context context;

    public AddressListAdapter(Context context, List<AllAddressListResponse.Datum> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
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
        TextView address_type,tv_addressLine1,tv_addressLine2,tv_city,tv_phone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address_type = (TextView) itemView.findViewById(R.id.address_type);
            tv_addressLine1 = (TextView) itemView.findViewById(R.id.tv_addressLine1);
            tv_addressLine2 = (TextView) itemView.findViewById(R.id.tv_addressLine2);
            tv_city = (TextView) itemView.findViewById(R.id.tv_city);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);

        }
    }
}
