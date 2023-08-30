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
import com.example.licious.listener.SubCategoriesListener;
import com.example.licious.response.GetCategoryResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Category_horizental_Adapter extends RecyclerView.Adapter<Category_horizental_Adapter.ViewHolder> {

    public static List<GetCategoryResponse.Datum> ItemList;
    private Context context;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";
    SubCategoriesListener listener;

    public Category_horizental_Adapter(Context context, List<GetCategoryResponse.Datum> ItemList,SubCategoriesListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
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
        holder.title.setText(ItemList.get(position).getName());
        if (ItemList.get(position).getImage() == null) {
            Picasso.with(context)
                    .load(R.drawable.noimagecircle)
                    .into(holder.iv_circle_image);
        }else {
            Picasso.with(context)
                    .load(image_url + ItemList.get(position).getImage())
                    .into(holder.iv_circle_image);
        }

    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView iv_circle_image;
        TextView title;
        LinearLayout ll_all;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_circle_image = (CircleImageView) itemView.findViewById(R.id.iv_circle_image);
            title = (TextView) itemView.findViewById(R.id.title);
            ll_all = (LinearLayout) itemView.findViewById(R.id.ll_all);
            ll_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickedCategories(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });
        }
    }
}
