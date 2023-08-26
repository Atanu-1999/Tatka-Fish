package com.example.licious.adapter.listener;

import com.example.licious.response.AllCaterogyResponse;
import com.example.licious.response.AllWishListResponse;

public interface AllCategoryListener {
    void onItemClickedItem(AllCaterogyResponse.Datum item, int position, int type);
}
