package com.example.licious.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licious.R;
import com.example.licious.fragment.response.Master_Category_Response;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.ViewHolder> {
    public static List<Master_Category_Response.Datum> ItemList;
    private Context context;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";

    public Category_Adapter(Context context, List<Master_Category_Response.Datum> ItemList) {
        this.ItemList = ItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public Category_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_category, parent, false);
        Category_Adapter.ViewHolder viewHolder = new Category_Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Category_Adapter.ViewHolder holder, int position) {
       // Master_Category_Model model = ItemList.get(position).getData().get(position).getName();
        holder.tv_catg_name.setText(ItemList.get(position).getName());
        Picasso.with(context)
                .load(image_url+ItemList.get(position).getImage())
                .into(holder.iv_image);
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView iv_image;
        TextView tv_catg_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = (CircleImageView) itemView.findViewById(R.id.iv_image);
            tv_catg_name = (TextView) itemView.findViewById(R.id.tv_catg_name);
        }
    }
}
