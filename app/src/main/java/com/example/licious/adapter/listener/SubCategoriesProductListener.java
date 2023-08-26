package com.example.licious.adapter.listener;

import com.example.licious.response.GetSubCategoryResponse;
import com.example.licious.response.SubCategoryItemResponse;

public interface SubCategoriesProductListener {
    void onItemClickedCategoriesProduct(SubCategoryItemResponse.Datum item, int position, int type);
    void onItemClickedCategoriesProductWishList(SubCategoryItemResponse.Datum item, int position, int type);
    void onItemClickedItem(SubCategoryItemResponse.Datum item, int position, int type);
}
