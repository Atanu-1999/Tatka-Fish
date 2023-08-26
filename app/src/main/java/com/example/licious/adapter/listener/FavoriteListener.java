package com.example.licious.adapter.listener;

import com.example.licious.response.AllWishListResponse;
import com.example.licious.response.CartDetailsResponse;

public interface FavoriteListener {
    void onItemClickedAdd(AllWishListResponse.Datum item, int position, int type);
}
