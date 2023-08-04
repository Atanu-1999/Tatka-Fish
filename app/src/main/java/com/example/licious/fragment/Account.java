package com.example.licious.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.MainActivity;
import com.example.licious.R;
import com.example.licious.activity.Address;
import com.example.licious.activity.ContactUs;
import com.example.licious.activity.FAQ;
import com.example.licious.activity.OTP_Verify;
import com.example.licious.activity.OrdersPage;
import com.example.licious.activity.Pages;
import com.example.licious.activity.Update_Profile;
import com.example.licious.activity.Wishlist;
import com.example.licious.api.ApiService;
import com.example.licious.authentication.DeviceUtils;
import com.example.licious.response.SendOtp_Response;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Account extends Fragment {

    TextView txt_edite, txt_login,tv_phone;
    LinearLayout address, notification, contactUs, privacy, faq, terms, btn_logout, btn_order, wishList, before_login_layout,
            after_login_layout,About_policy;
    private BottomSheetDialog bottomSheetDialog;
    private Handler handler;
    private int charIndex;
    String deviceId, phone;
    String BlankId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View account = inflater.inflate(R.layout.fragment_account, container, false);
        /*Device Id Get*/
        deviceId = DeviceUtils.getDeviceId(getContext());
        Log.e("Device Id", "" + deviceId);
        /*Value Store*/
        SharedPreferences loginPref = getContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = loginPref.edit();
//        editor.putString("ID","0");
//        editor.commit();

        About_policy = account.findViewById(R.id.About_policy);
        after_login_layout = account.findViewById(R.id.after_login_layout);
        before_login_layout = account.findViewById(R.id.before_login_layout);
        wishList = account.findViewById(R.id.wishList);
        btn_order = account.findViewById(R.id.btn_order);
        txt_login = account.findViewById(R.id.txt_login);
        btn_logout = account.findViewById(R.id.btn_logout);
        privacy = account.findViewById(R.id.privacy);
        faq = account.findViewById(R.id.faq);
        terms = account.findViewById(R.id.terms);
        contactUs = account.findViewById(R.id.contactUs);
        notification = account.findViewById(R.id.notification);
        address = account.findViewById(R.id.address);
        txt_edite = account.findViewById(R.id.txt_edite);
        tv_phone = account.findViewById(R.id.tv_phone);

        String phoneNum= loginPref.getString("phone","");
        tv_phone.setText(phoneNum);

        Log.d("device_id",loginPref.getString("device_id", ""));
        if (BlankId.equals(loginPref.getString("device_id", ""))) {
            after_login_layout.setVisibility(View.GONE);
            before_login_layout.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.GONE);
        } else {
            after_login_layout.setVisibility(View.VISIBLE);
            before_login_layout.setVisibility(View.GONE);
            btn_logout.setVisibility(View.VISIBLE);
        }
        txt_edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Update_Profile.class));
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Address.class));
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ContactUs.class));
            }
        });
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FAQ.class));
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),Pages.class);
                i.putExtra("page_id","2");
                startActivity(new Intent(i));
            }
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(getContext(),Pages.class);
               i.putExtra("page_id","1");
               startActivity(new Intent(i));
            }
        });
        About_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),Pages.class);
                i.putExtra("page_id","3");
                startActivity(new Intent(i));
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences =getContext().getSharedPreferences("login_pref",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();

                Intent logoutIntent = new Intent(getContext(), MainActivity.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);
                Toast.makeText(getContext(),"LogOut Successfully",Toast.LENGTH_SHORT).show();
            }
        });
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OrdersPage.class));
            }
        });
        wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Wishlist.class));
            }
        });
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetTheme);
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.login_layout,
                        (LinearLayout) getActivity().findViewById(R.id.container));
                TextView btn_submit = (TextView) view1.findViewById(R.id.btn_submit);
                TextView myTextView = (TextView) view1.findViewById(R.id.myTextView);
                EditText txt_phone = (EditText) view1.findViewById(R.id.txt_phone);
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        phone = txt_phone.getText().toString();
                        if (txt_phone.getText().toString().trim().isEmpty()) {
                            Toast.makeText(getContext(), "Field Can't Empty", Toast.LENGTH_SHORT).show();
                            txt_phone.requestFocus();
                        } else if (txt_phone.length() > 10 || txt_phone.length() < 10) {
                            Toast.makeText(getContext(), "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                            txt_phone.requestFocus();
                        } else {
                            user_login(phone);
                        }
                    }
                });
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();
                bottomSheetDialog.setCanceledOnTouchOutside(false);
            }
        });

        return account;
    }

    private void user_login(String phone) {
        Call<SendOtp_Response> login_apiCall = ApiService.apiHolders().sent_otp(phone, deviceId);
        login_apiCall.enqueue(new Callback<SendOtp_Response>() {
            @Override
            public void onResponse(Call<SendOtp_Response> call, Response<SendOtp_Response> response) {
                if (response.isSuccessful()) {
                    String response1 = response.body().toString();
                    Intent i = new Intent(getContext(), OTP_Verify.class);
                    i.putExtra("OTP", response.body().getData().get(0).getOtp());
                    i.putExtra("NUMBER", response.body().getData().get(0).getPhone());
                    i.putExtra("DEVICE_ID", response.body().getData().get(0).getDeviceId());
                    startActivity(i);
                } else {

                }
            }

            @Override
            public void onFailure(Call<SendOtp_Response> call, Throwable t) {

            }
        });
    }
}