package com.example.licious.listener;

import com.example.licious.response.GetCategoryResponse;
import com.example.licious.response.SearchResponse;

public interface SearchItemClickListener {
    void onItemClickedSearchItem(SearchResponse.Datum item, int position, int type);
}
