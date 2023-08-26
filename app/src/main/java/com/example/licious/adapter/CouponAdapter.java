package com.example.licious.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licious.R;
import com.example.licious.listener.CouponListener;
import com.example.licious.response.CouponsResponse;

import java.util.List;

public class CouponAdapter  extends RecyclerView.Adapter<CouponAdapter.ViewHolder>{
    public static List<CouponsResponse.Datum> ItemList;
    private Context context;
    Boolean flag = false;
    CouponListener listener;

    public CouponAdapter(Context context, List<CouponsResponse.Datum> ItemList,CouponListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public CouponAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_coupon, parent, false);
        CouponAdapter.ViewHolder viewHolder = new CouponAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CouponAdapter.ViewHolder holder, int position) {
        holder.tv_coupon_code.setText(ItemList.get(position).getCoupon_code());
        holder.tv_descrp.setText(ItemList.get(position).getDescription());
        holder.tv_termCondition.setText(ItemList.get(position).getTc());

        holder.txt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    holder.hide_layout.setVisibility(View.VISIBLE);
                    flag=true;
                    holder.txt_view.setText("Hide details");
                }else {
                    holder.hide_layout.setVisibility(View.GONE);
                    flag=false;
                    holder.txt_view.setText("View details");
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
        TextView tv_coupon_code,tv_descrp,tv_apply,txt_view,tv_termCondition;
        LinearLayout hide_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_coupon_code = (TextView) itemView.findViewById(R.id.tv_coupon_code);
            tv_descrp = (TextView) itemView.findViewById(R.id.tv_descrp);
            tv_apply = (TextView) itemView.findViewById(R.id.tv_apply);
            txt_view = (TextView) itemView.findViewById(R.id.txt_view);
            tv_termCondition = (TextView) itemView.findViewById(R.id.tv_termCondition);
            hide_layout = (LinearLayout) itemView.findViewById(R.id.hide_layout);

            tv_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickedCoupon(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });

        }
    }
}
