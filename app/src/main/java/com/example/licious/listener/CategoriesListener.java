package com.example.licious.listener;

import com.example.licious.response.Best_Seller_Response;
import com.example.licious.response.Master_Category_Response;

public interface CategoriesListener {
    void onItemClickedCategories(Master_Category_Response.Datum item, int position, int type);
}
