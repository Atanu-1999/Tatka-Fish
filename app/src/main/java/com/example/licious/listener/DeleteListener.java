package com.example.licious.listener;

import com.example.licious.response.CartDetailsResponse;

public interface DeleteListener {
    void onItemClickedDelete(CartDetailsResponse.Datum item, int position, int type);
    void onItemClickedAdditem(CartDetailsResponse.Datum item, int position, int type);
    void onItemClickedRemoveItem(CartDetailsResponse.Datum item, int position, int type);
}
