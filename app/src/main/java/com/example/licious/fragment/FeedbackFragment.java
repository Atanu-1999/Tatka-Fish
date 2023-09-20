package com.example.licious.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.example.licious.R;
import com.example.licious.activity.SendFeedbackActivity;
import com.example.licious.api.ApiService;
import com.example.licious.response.RatingResponse;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeedbackFragment extends Fragment {
    int orderId;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    int id;
    String token;
    ProgressDialog progressDialog;
    RatingBar ratingBar;
    EditText et_message;
    LinearLayout btn_submit;
    RelativeLayout rl_view;
    ImageView back;
    int order_id,id_his;
    String orderHistoryDetails,orderhistory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View feedback = inflater.inflate(R.layout.fragment_feedback, container, false);
        ratingBar = feedback.findViewById(R.id.ratingBar);
        et_message = feedback.findViewById(R.id.et_message);
        btn_submit = feedback.findViewById(R.id.btn_submit);
        rl_view =feedback.findViewById(R.id.rl_view);
        back = feedback.findViewById(R.id.back);

        LayerDrawable stars=(LayerDrawable)ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);

        //loading
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");


        Bundle bundle =getArguments();
        //Extract the data…
        if (bundle != null) {
            orderId = bundle.getInt("order_id", 0);
            id_his= bundle.getInt("id", 0);
            orderHistoryDetails = bundle.getString("orderHistoryDetails",null);
            orderhistory = bundle.getString("orderhistory",orderhistory);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderHistoryDetails.equals("orderHistoryDetails")) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id_his);
                    bundle.putString("orderHistoryDetails", orderHistoryDetails);
                    bundle.putString("orderhistory",orderhistory);
//                    Intent i = new Intent(getContext(), OrderHistoryDetailsFragment.class);
//                    i.putExtras(bundle);
//                    startActivity(i);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    OrderHistoryDetailsFragment orderHistoryDetailsFragment = new OrderHistoryDetailsFragment();
                    orderHistoryDetailsFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.main_container, orderHistoryDetailsFragment);
                    //edit_sku_no.getText().clear();
                    fragmentTransaction.addToBackStack(null).commit();
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count = String.valueOf(ratingBar.getRating());
                //Toast.makeText(OrderHistoryTracking.this,count,Toast.LENGTH_SHORT).show();
                String desc = et_message.getText().toString();

                ratingAPi(orderId,count,desc);
            }
        });



        return feedback;
    }

    private void ratingAPi(int orderId, String count, String desc) {
        progressDialog.show();
        Call<RatingResponse> category_apiCall = ApiService.apiHolders().ratingfeedback(id,orderId,count,desc,token);
        category_apiCall.enqueue(new Callback<RatingResponse>() {
            @Override
            public void onResponse(Call<RatingResponse> call, Response<RatingResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    progressDialog.dismiss();
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_view, "Successfully submit", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                    errorBar.show();
                   // finish();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id_his);
                    bundle.putString("orderHistoryDetails", orderHistoryDetails);
                    bundle.putString("orderhistory",orderhistory);
//                    Intent i = new Intent(getContext(), OrderHistoryDetailsFragment.class);
//                    i.putExtras(bundle);
//                    startActivity(i);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    OrderHistoryDetailsFragment orderHistoryDetailsFragment = new OrderHistoryDetailsFragment();
                    orderHistoryDetailsFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.main_container, orderHistoryDetailsFragment);
                    //edit_sku_no.getText().clear();
                    fragmentTransaction.addToBackStack(null).commit();

                } else {
                    progressDialog.dismiss();
                    Snackbar errorBar;
                    errorBar = Snackbar.make(rl_view, "failed", Snackbar.LENGTH_LONG);
                    errorBar.setTextColor(getResources().getColor(R.color.white));
                    errorBar.setActionTextColor(getResources().getColor(R.color.white));
                    errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                    errorBar.show();
                }
            }

            @Override
            public void onFailure(Call<RatingResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar errorBar;
                errorBar = Snackbar.make(rl_view, "failed", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();
            }
        });
    }
}