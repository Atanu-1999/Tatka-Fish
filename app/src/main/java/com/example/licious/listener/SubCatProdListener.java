package com.example.licious.listener;

import com.example.licious.response.SubCategoryItemResponse;

public interface SubCatProdListener {
    void onItemClickedCategoriesProduct(SubCategoryItemResponse.Datum item, int position, int type);
    void onItemClickedCategoriesProductWishList(SubCategoryItemResponse.Datum item, int position, int type);
    void onItemClickedItem(SubCategoryItemResponse.Datum item, int position, int type);
}
