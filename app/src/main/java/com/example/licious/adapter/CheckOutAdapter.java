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
import com.example.licious.listener.DeleteListener;
import com.example.licious.response.CartDetailsResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.ViewHolder>{
    public static List<CartDetailsResponse.Datum> ItemList;
    private Context context;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";
    private DeleteListener listener;

    public CheckOutAdapter(Context context, List<CartDetailsResponse.Datum> ItemList,DeleteListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CheckOutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_checkout, parent, false);
        CheckOutAdapter.ViewHolder viewHolder = new CheckOutAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutAdapter.ViewHolder holder, int position) {
        holder.tv_tittle_prods.setText(ItemList.get(position).getProduct_title());
        holder.tv_weight.setText(ItemList.get(position).getWeight() + " " + ItemList.get(position).getWeight_type());
        holder.tv_price.setText("₹" + ItemList.get(position).getPrice());
        holder.basePrice.setText(ItemList.get(position).getMrp());
        holder.tv_qnt.setText("Qty" + " " +ItemList.get(position).getQty());
        Picasso.with(context)
                .load(image_url+ItemList.get(position).getProduct_image())
                .into(holder.iv_icon);
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Iv_delete;
        CircleImageView iv_icon;
        TextView tv_qnt,basePrice,tv_price,tv_weight,tv_tittle_prods;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Iv_delete =  (ImageView) itemView.findViewById(R.id.Iv_delete);
            iv_icon =  (CircleImageView) itemView.findViewById(R.id.iv_icon);
            tv_qnt = (TextView) itemView.findViewById(R.id.tv_qnt);
            basePrice = (TextView) itemView.findViewById(R.id.basePrice);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_weight = (TextView) itemView.findViewById(R.id.tv_weight);
            tv_tittle_prods = (TextView) itemView.findViewById(R.id.tv_tittle_prods);

            Iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickedDelete(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });
        }
    }
}
