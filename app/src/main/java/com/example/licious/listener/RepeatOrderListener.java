package com.example.licious.listener;

import com.example.licious.response.Best_Seller_Response;
import com.example.licious.response.OrderHistoryDataResponse;

public interface RepeatOrderListener {
    void onItemClickedRepeatOrder(OrderHistoryDataResponse.Datum item, int position, int type);
    void onItemClickedFeedback(OrderHistoryDataResponse.Datum item, int position, int type);
}
