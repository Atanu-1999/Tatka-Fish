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
import com.example.licious.response.Category_Response;
import com.example.licious.response.Master_Category_Response;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Category_horizental_Adapter extends RecyclerView.Adapter<Category_horizental_Adapter.ViewHolder> {

    public static List<Category_Response.Datum> ItemList;
    private Context context;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";

    public Category_horizental_Adapter(Context context, List<Category_Response.Datum> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
    }
    @NonNull
    @Override
    public Category_horizental_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sub_categories, parent, false);
        Category_horizental_Adapter.ViewHolder viewHolder = new Category_horizental_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Category_horizental_Adapter.ViewHolder holder, int position) {
        holder.title.setText(ItemList.get(position).getProduct_title());
        Picasso.with(context)
                .load(image_url+ItemList.get(position).getProduct_image())
                .into(holder.iv_circle_image);

    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView iv_circle_image;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_circle_image = (CircleImageView) itemView.findViewById(R.id.iv_circle_image);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
