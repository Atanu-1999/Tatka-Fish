package com.example.licious.listener;

import com.example.licious.response.Category_Response;
import com.example.licious.response.GetCategoryResponse;

public interface SubCategoriesListener {
    void onItemClickedCategories(GetCategoryResponse.Datum item, int position, int type);
}
