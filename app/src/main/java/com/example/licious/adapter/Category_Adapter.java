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
import com.example.licious.response.GetMasterCategoryResponse;
import com.example.licious.response.Master_Category_Response;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.ViewHolder> {
    public static List<GetMasterCategoryResponse.Datum> ItemList;
    private Context context;
    private final Category_Adapter.OnItemClickListener listener;
    String image_url = "https://tatkafish.in/superuser/public/uploads/";
    Category_horizental_Adapter category_horizental_adapter;
    public interface OnItemClickListener {
        void onItemClickCategory(GetMasterCategoryResponse.Datum item, int position, int type);
    }

    public Category_Adapter(Context context, List<GetMasterCategoryResponse.Datum> ItemList,Category_Adapter.OnItemClickListener listener) {
        this.ItemList = ItemList;
        this.context = context;
        this.listener = listener;
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

      //  category_horizental_adapter = new Category_horizental_Adapter()
    }

    @Override
    public int getItemCount() {
        if (ItemList == null) return 0;
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView iv_image;
        TextView tv_catg_name;
        ImageView open;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = (CircleImageView) itemView.findViewById(R.id.iv_image);
            tv_catg_name = (TextView) itemView.findViewById(R.id.tv_catg_name);
            open = (ImageView) itemView.findViewById(R.id.open);
            open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickCategory(ItemList.get(getAdapterPosition()), getAdapterPosition(), 1);
                }
            });
        }
    }
}
