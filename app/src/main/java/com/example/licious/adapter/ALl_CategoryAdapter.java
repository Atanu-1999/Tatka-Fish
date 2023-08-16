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
import com.example.licious.listener.AllCategoryListener;
import com.example.licious.listener.SubCategoriesListener;
import com.example.licious.response.AllCaterogyResponse;
import com.example.licious.response.GetCategoryResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ALl_CategoryAdapter extends RecyclerView.Adapter<ALl_CategoryAdapter.ViewHolder> {
    public static List<AllCaterogyResponse.Datum> ItemList;
    private Context context;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";
    AllCategoryListener listener;

    public ALl_CategoryAdapter(Context context, List<AllCaterogyResponse.Datum> ItemList,AllCategoryListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ALl_CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sub_categories, parent, false);
        ALl_CategoryAdapter.ViewHolder viewHolder = new ALl_CategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ALl_CategoryAdapter.ViewHolder holder, int position) {
        holder.title.setText(ItemList.get(position).getName());
        Picasso.with(context)
                .load(image_url+ItemList.get(position).getImage())
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
        LinearLayout ll_all;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_circle_image = (CircleImageView) itemView.findViewById(R.id.iv_circle_image);
            title = (TextView) itemView.findViewById(R.id.title);
            ll_all = (LinearLayout) itemView.findViewById(R.id.ll_all);

            ll_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickedItem(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });
        }
    }
}
