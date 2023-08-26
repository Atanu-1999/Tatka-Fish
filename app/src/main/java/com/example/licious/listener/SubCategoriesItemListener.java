package com.example.licious.listener;

import com.example.licious.response.Category_Response;
import com.example.licious.response.GetSubCategoryResponse;
import com.example.licious.response.SubCategoriesResponse;
import com.example.licious.response.SubCategoryItemResponse;

public interface SubCategoriesItemListener {
    void onItemClickedCategoriesItem(GetSubCategoryResponse.Datum item, int position, int type);
}
