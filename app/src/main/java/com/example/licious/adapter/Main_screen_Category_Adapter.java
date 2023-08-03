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
import com.example.licious.response.Master_Category_Response;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Main_screen_Category_Adapter extends RecyclerView.Adapter<Main_screen_Category_Adapter.ViewHolder>{
    public static List<Master_Category_Response.Datum> ItemList;
    private Context context;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";

    public Main_screen_Category_Adapter(Context context, List<Master_Category_Response.Datum> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
    }
    @NonNull
    @Override
    public Main_screen_Category_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cetegory_layout, parent, false);
        Main_screen_Category_Adapter.ViewHolder viewHolder = new Main_screen_Category_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Main_screen_Category_Adapter.ViewHolder holder, int position) {
        holder.tv_name.setText(ItemList.get(position).getName());
        Picasso.with(context)
                .load(image_url+ItemList.get(position).getImage())
                .into(holder.iv_img);

    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView tv_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img =  (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);

        }
    }
}
