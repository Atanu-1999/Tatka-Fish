package com.example.licious.listener;

import com.example.licious.response.Category_Response;
import com.example.licious.response.HistoryResponse;

public interface OrderHistoryListener {
    void onItemClickedOrderHistory(HistoryResponse.Datum item, int position, int type);
}
