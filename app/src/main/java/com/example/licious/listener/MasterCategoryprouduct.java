package com.example.licious.listener;

import com.example.licious.response.Category_Response;
import com.example.licious.response.SubCategoryItemResponse;

public interface MasterCategoryprouduct {
    void onItemClickedMasterCategoriesProductWishList(Category_Response.Datum item, int position, int type);
    void onItemClickedMasterCategoriesProductADDcart(Category_Response.Datum item, int position, int type);
}
