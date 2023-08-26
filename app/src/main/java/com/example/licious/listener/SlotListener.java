package com.example.licious.listener;

import com.example.licious.response.Category_Response;
import com.example.licious.response.SlotResponse;

public interface SlotListener {
    void onItemClickedSlot(SlotResponse.Datum item, int position, Boolean flag);
}
