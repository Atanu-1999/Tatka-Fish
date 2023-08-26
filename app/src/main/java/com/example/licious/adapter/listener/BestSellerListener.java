package com.example.licious.adapter.listener;

import com.example.licious.response.Best_Seller_Response;

public interface BestSellerListener {
    void onItemClickedmy(Best_Seller_Response.Datum item, int position, int type);
    void onItemClickedWishList(Best_Seller_Response.Datum item, int position, int type,Boolean status);
    void onItemClickedItem(Best_Seller_Response.Datum item, int position, int type);
}
