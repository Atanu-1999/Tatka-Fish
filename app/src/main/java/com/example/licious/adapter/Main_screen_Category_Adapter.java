package com.example.licious.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licious.R;
import com.example.licious.adapter.listener.CategoriesListener;
import com.example.licious.response.GetMasterCategoryResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Main_screen_Category_Adapter extends RecyclerView.Adapter<Main_screen_Category_Adapter.ViewHolder>{
    public static List<GetMasterCategoryResponse.Datum> ItemList;
    private Context context;
    private CategoriesListener listener;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";

    public Main_screen_Category_Adapter(Context context, List<GetMasterCategoryResponse.Datum> ItemList,CategoriesListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
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
        LinearLayout ll_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img =  (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickedCategories(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });

        }
    }
}
