package com.example.licious.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.licious.R;
import com.example.licious.activity.OrderHistoryTracking;
import com.example.licious.activity.OrdersPage;
import com.example.licious.adapter.OrderHistoryAdapter;
import com.example.licious.adapter.OrderHistoryDataAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.listener.OrderHistoryListener;
import com.example.licious.response.HistoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderHistoryFragment extends Fragment {
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    int id;
    String token;
    ProgressDialog progressDialog;
    List<HistoryResponse.Datum> historyResponse;
    TextView tv_date, tv_orderId, tv_status, txt_noData;
    OrderHistoryAdapter orderHistoryAdapter;
    RecyclerView rv_oderHistory;
    OrderHistoryDataAdapter orderHistoryDataAdapter;
    ImageView back;
    String account;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View order_History = inflater.inflate(R.layout.fragment_order_history, container, false);
        tv_date = order_History.findViewById(R.id.tv_date);
        tv_orderId = order_History.findViewById(R.id.tv_orderId);
        tv_status = order_History.findViewById(R.id.tv_status);
        rv_oderHistory = order_History.findViewById(R.id.rv_oderHistory);
        back = order_History.findViewById(R.id.back);
        txt_noData = order_History.findViewById(R.id.txt_noData);

        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");


        Bundle bundle = getArguments();
        if (bundle != null) {
            account = bundle.getString("account");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(OrdersPage.this, MainActivity.class);
//                startActivity(intent);
                if (account.equals("account")) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Account account = new Account();
//                subCategoriesFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.main_container, account);
                    //edit_sku_no.getText().clear();
                    fragmentTransaction.addToBackStack(null).commit();
                }
            }
        });

        getOrderDatahistory();//call APi

        return order_History;
    }

    private void getOrderDatahistory() {
        progressDialog.show();
        Call<HistoryResponse> category_apiCall = ApiService.apiHolders().getOrderHistory(id, token);
        category_apiCall.enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    historyResponse = response.body().getData();
                    progressDialog.dismiss();
                    orderHistoryDataAdapter = new OrderHistoryDataAdapter(getContext(), historyResponse, new OrderHistoryListener() {
                        @Override
                        public void onItemClickedOrderHistory(HistoryResponse.Datum item, int position, int type) {
                            Bundle bundle = new Bundle();
                            int id = item.getId();
//                            bundle.putInt("id", id);
//                            Intent intent = new Intent(getContext(), OrderHistoryTracking.class);
//                            intent.putExtras(bundle);
//                            startActivity(intent);
                            bundle.putInt("id", id);
                            bundle.putString("account", "account");
                            bundle.putString("orderhistory", "orderhistory");
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            OrderHistoryDetailsFragment orderHistoryDetailsFragment = new OrderHistoryDetailsFragment();
                            orderHistoryDetailsFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.main_container, orderHistoryDetailsFragment);
                            //edit_sku_no.getText().clear();
                            fragmentTransaction.addToBackStack(null).commit();
                        }
                    });
                    rv_oderHistory.setAdapter(orderHistoryDataAdapter);
                    rv_oderHistory.setLayoutManager(new LinearLayoutManager(getContext()));
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                progressDialog.dismiss();
                txt_noData.setVisibility(View.VISIBLE);
                rv_oderHistory.setVisibility(View.GONE);
            }
        });
    }
}