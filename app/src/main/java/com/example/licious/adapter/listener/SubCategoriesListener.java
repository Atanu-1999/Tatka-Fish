package com.example.licious.adapter.listener;

import com.example.licious.response.Category_Response;
import com.example.licious.response.GetCategoryResponse;

public interface SubCategoriesListener {
    void onItemClickedCategories(GetCategoryResponse.Datum item, int position, int type);
}
