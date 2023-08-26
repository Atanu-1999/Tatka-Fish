package com.example.licious.adapter.listener;

import com.example.licious.response.Best_Seller_Response;
import com.example.licious.response.CouponsResponse;

public interface CouponListener {
    void onItemClickedCoupon(CouponsResponse.Datum item, int position, int type);
}
