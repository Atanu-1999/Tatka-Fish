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
import com.example.licious.listener.SubCategoriesItemListener;
import com.example.licious.response.GetSubCategoryResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Sub_categoryAdapter extends RecyclerView.Adapter<Sub_categoryAdapter.ViewHolder> {
    public static List<GetSubCategoryResponse.Datum> ItemList;
    private Context context;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";
    SubCategoriesItemListener listener;

    public Sub_categoryAdapter(Context context, List<GetSubCategoryResponse.Datum> ItemList, SubCategoriesItemListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Sub_categoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_subcategories, parent, false);
        Sub_categoryAdapter.ViewHolder viewHolder = new Sub_categoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Sub_categoryAdapter.ViewHolder holder, int position) {
        holder.tv_title_product.setText(ItemList.get(position).getName());
        if (ItemList.get(position).getImage() == null) {
            Picasso.with(context)
                    .load(R.drawable.noimagecircle)
                    .into(holder.cv_image);
        } else {
            Picasso.with(context)
                    .load(image_url + ItemList.get(position).getImage())
                    .into(holder.cv_image);
        }

    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cv_image;
        TextView tv_title_product;
        LinearLayout ll_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_image = (CircleImageView) itemView.findViewById(R.id.cv_image);
            tv_title_product = (TextView) itemView.findViewById(R.id.tv_title_products);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_items);

            ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickedCategoriesItem(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });
        }
    }
}
