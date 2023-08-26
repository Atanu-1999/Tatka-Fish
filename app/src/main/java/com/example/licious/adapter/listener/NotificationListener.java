package com.example.licious.adapter.listener;

import com.example.licious.response.AllWishListResponse;
import com.example.licious.response.NotificationListResponse;

public interface NotificationListener {
    void onItemClicked(NotificationListResponse.Datum item, int position, int type);
}
