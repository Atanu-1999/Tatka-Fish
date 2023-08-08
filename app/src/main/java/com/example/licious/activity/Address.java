package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.adapter.AddressListAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.fragment.response.AddAddressResponse;
import com.example.licious.fragment.response.AllAddressListResponse;
import com.example.licious.fragment.response.DeleteResponse;
import com.example.licious.fragment.response.EditAddressResponse;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Address extends AppCompatActivity {

    ImageView back;
    TextView txt_add;
    private BottomSheetDialog bottomSheetDialog;
    TextInputEditText tv_area, tv_building, tv_landmark, tv_city, tv_mobile;
    SharedPreferences loginPref;
    SharedPreferences.Editor editor;
    String token, phoneNum;
    int id;
    RecyclerView rv_all_address;
    List<AllAddressListResponse.Datum> allAddressList;
    AddressListAdapter addressListAdapter;

    AddressListAdapter.OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        txt_add = findViewById(R.id.txt_add);
        rv_all_address = findViewById(R.id.rv_all_address);
        back = findViewById(R.id.back);

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);
        phoneNum = loginPref.getString("phone", "");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getAllAddress();
        txt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(Address.this, R.style.BottomSheetTheme);
                View view1 = LayoutInflater.from(Address.this).inflate(R.layout.address_layout,
                        (LinearLayout) findViewById(R.id.container));
                TextView btn_save = (TextView) view1.findViewById(R.id.btn_save);
                tv_area = (TextInputEditText) view1.findViewById(R.id.tv_area);
                tv_building = (TextInputEditText) view1.findViewById(R.id.tv_building);
                tv_landmark = (TextInputEditText) view1.findViewById(R.id.tv_landmark);
                tv_city = (TextInputEditText) view1.findViewById(R.id.tv_city);
                tv_mobile = (TextInputEditText) view1.findViewById(R.id.tv_mobile);
                tv_mobile.setText(phoneNum);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkValidation()) {
                            //Toast.makeText(Address.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();
                            Add_Address();
                            bottomSheetDialog.dismiss();
                        }
                    }
                });
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();
                bottomSheetDialog.setCanceledOnTouchOutside(false);
            }
        });
    }

    private void getAllAddress() {
        Call<AllAddressListResponse> getAllAddress = ApiService.apiHolders().getAllAddress(id, token);
        getAllAddress.enqueue(new Callback<AllAddressListResponse>() {
            @Override
            public void onResponse(Call<AllAddressListResponse> call, Response<AllAddressListResponse> response) {
                Toast.makeText(Address.this, "Successfully", Toast.LENGTH_SHORT).show();
                allAddressList = response.body().getData();
                addressListAdapter = new AddressListAdapter(getApplication(), allAddressList, new AddressListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(AllAddressListResponse.Datum item, int position, int type) {
                        int id = item.getAddress_id();
                        deleteAddress(id);
                    }

                    @Override
                    public void onItemClickEdit(AllAddressListResponse.Datum item, int position, int type) {
                        Toast.makeText(Address.this, "Hello", Toast.LENGTH_SHORT).show();
                        EditAddress(item, position);
                    }
                });
                rv_all_address.setAdapter(addressListAdapter);
                rv_all_address.setLayoutManager(new LinearLayoutManager(getApplication()));

            }

            @Override
            public void onFailure(Call<AllAddressListResponse> call, Throwable t) {
                Toast.makeText(Address.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void EditAddress(AllAddressListResponse.Datum item, int position) {
        bottomSheetDialog = new BottomSheetDialog(Address.this, R.style.BottomSheetTheme);
        View view1 = LayoutInflater.from(Address.this).inflate(R.layout.address_layout,
                (LinearLayout) findViewById(R.id.container));
        TextView btn_save = (TextView) view1.findViewById(R.id.btn_save);
        tv_area = (TextInputEditText) view1.findViewById(R.id.tv_area);
        tv_building = (TextInputEditText) view1.findViewById(R.id.tv_building);
        tv_landmark = (TextInputEditText) view1.findViewById(R.id.tv_landmark);
        tv_city = (TextInputEditText) view1.findViewById(R.id.tv_city);
        tv_mobile = (TextInputEditText) view1.findViewById(R.id.tv_mobile);

        tv_area.setText(item.getAddress_line_one());
        String addrs_one = item.getAddress_line_one();
        tv_building.setText(item.getAddress_line_two());
        String addrs_second = item.getAddress_line_two();
        tv_landmark.setText(item.getLandmark());
        String landMark = item.getLandmark();
        tv_city.setText(item.getCity());
        String city = item.getCity();
        tv_mobile.setText(item.getMobile_number());
        String phone = item.getMobile_number();
        int address_id=item.getAddress_id();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    //Toast.makeText(Address.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();
                    UpdateAddress(address_id,addrs_one, addrs_second, landMark, city, phone);
                    bottomSheetDialog.dismiss();
                }
            }
        });
        bottomSheetDialog.setContentView(view1);
        bottomSheetDialog.show();
        bottomSheetDialog.setCanceledOnTouchOutside(false);
    }

    private void UpdateAddress(int address_id,String addrs_one, String addrs_second, String landMark, String city, String phone) {
        Call<EditAddressResponse> editAddress = ApiService.apiHolders().update_address(token,address_id,addrs_one,addrs_second,landMark,city,phone,"Home");
        editAddress.enqueue(new Callback<EditAddressResponse>() {
            @Override
            public void onResponse(Call<EditAddressResponse> call, Response<EditAddressResponse> response) {
                Toast.makeText(Address.this, "Address updated Successfully", Toast.LENGTH_SHORT).show();
                getAllAddress();
            }

            @Override
            public void onFailure(Call<EditAddressResponse> call, Throwable t) {
                Toast.makeText(Address.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void deleteAddress(int id) {
        Call<DeleteResponse> addAddress = ApiService.apiHolders().deleteAddress(id, token);
        addAddress.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                Toast.makeText(Address.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();
                getAllAddress();
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(Address.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Add_Address() {
        String area = tv_area.getText().toString();
        String building = tv_building.getText().toString();
        String landmark = tv_landmark.getText().toString();
        String city = tv_city.getText().toString();

        Call<AddAddressResponse> addAddress = ApiService.apiHolders().add_address(id, area, building, landmark, city,
                phoneNum, "Home", token);
        addAddress.enqueue(new Callback<AddAddressResponse>() {
            @Override
            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {
                Toast.makeText(Address.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();
                getAllAddress();
            }

            @Override
            public void onFailure(Call<AddAddressResponse> call, Throwable t) {
                Toast.makeText(Address.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkValidation() {
        if (tv_area.getText().toString().trim().length() == 0) {
            tv_area.setError("Please enter Area");
            tv_area.requestFocus();
            return false;
        } else if (tv_building.getText().toString().trim().length() == 0) {
            tv_building.setError("please enter building");
            tv_building.requestFocus();
            return false;
        } else if (tv_city.getText().toString().trim().length() == 0) {
            tv_city.setError("please enter city");
            tv_city.requestFocus();
            return false;
        }
//        else if (selectType.equals("doctor")) {
//            if (et_specialization.getText().toString().trim().length() == 0) {
//                et_specialization.setError("please enter specialization");
//                et_specialization.requestFocus();
//                return false;
//            }
//        }
        return true;
    }
}