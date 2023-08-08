package com.example.licious;

import com.example.licious.response.Best_Seller_Response;

public interface BestSellerListener {
    void onItemClickedmy(int position);
    void onItemClickedWishList(Best_Seller_Response.Datum item, int position, int type,Boolean status);
}
