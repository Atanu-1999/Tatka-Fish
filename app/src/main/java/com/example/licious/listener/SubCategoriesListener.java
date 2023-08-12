package com.example.licious.listener;

import com.example.licious.response.Category_Response;

public interface SubCategoriesListener {
    void onItemClickedCategories(Category_Response.Datum item, int position, int type);
}
