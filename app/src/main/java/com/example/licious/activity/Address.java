package com.example.licious.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licious.R;
import com.example.licious.adapter.AddressListAdapter;
import com.example.licious.api.ApiService;
import com.example.licious.fragment.Account;
import com.example.licious.response.AddAddressResponse;
import com.example.licious.response.AllAddressListResponse;
import com.example.licious.response.DeleteResponse;
import com.example.licious.response.EditAddressResponse;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Address extends AppCompatActivity {

    ImageView back;
    TextView txt_add, tv_home, tv_work, tv_other;
    private BottomSheetDialog bottomSheetDialog;
    TextInputEditText tv_area, tv_building, tv_landmark, tv_city, tv_mobile;
    SharedPreferences loginPref, address_pref;
    SharedPreferences.Editor editor, editor1;
    String token, phoneNum;
    int id;
    RecyclerView rv_all_address;
    List<AllAddressListResponse.Datum> allAddressList;
    AddressListAdapter addressListAdapter;

    AddressListAdapter.OnItemClickListener listener;
    String address_type = "";
    ProgressDialog progressDialog;
    TextView txt_noData;
    String BlankId = "";
    RelativeLayout Rl_layout;
    int addr_type;
    int add_Id;
    String adds_one;
    String adds_two;
    String add_type;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        txt_add = findViewById(R.id.txt_add);
        rv_all_address = findViewById(R.id.rv_all_address);
        back = findViewById(R.id.back);
        txt_noData = findViewById(R.id.txt_noData);
        Rl_layout = findViewById(R.id.Rl_layout);

        loginPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        address_pref = getSharedPreferences("address_pref", Context.MODE_PRIVATE);
        editor = loginPref.edit();
        editor1 = address_pref.edit();
        token = loginPref.getString("device_id", null);
        id = loginPref.getInt("userId", 0);
        phoneNum = loginPref.getString("phone", "");

        //loading
        progressDialog = new ProgressDialog(Address.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        if (bundle != null) {
            addr_type = bundle.getInt("addr_type", 0);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor1.putInt("add_id", add_Id);
                editor1.putString("adds_one", adds_one);
                editor1.putString("adds_two", adds_two);
                editor1.putString("add_type", add_type);
                editor1.putString("city", city);
                editor1.commit();
                finish();
            }
        });

        getAllAddress();
        txt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BlankId.equals(loginPref.getString("device_id", ""))) {
                    Toast.makeText(getApplicationContext(), "Please Login First", Toast.LENGTH_SHORT).show();
                } else {
                    bottomSheetDialog = new BottomSheetDialog(Address.this, R.style.BottomSheetTheme);
                    View view1 = LayoutInflater.from(Address.this).inflate(R.layout.address_layout,
                            (LinearLayout) findViewById(R.id.container));
                    TextView btn_save = (TextView) view1.findViewById(R.id.btn_save);
                    tv_area = (TextInputEditText) view1.findViewById(R.id.tv_area);
                    tv_building = (TextInputEditText) view1.findViewById(R.id.tv_building);
                    tv_landmark = (TextInputEditText) view1.findViewById(R.id.tv_landmark);
                    tv_city = (TextInputEditText) view1.findViewById(R.id.tv_city);
                    tv_mobile = (TextInputEditText) view1.findViewById(R.id.tv_mobile);
                    tv_home = (TextView) view1.findViewById(R.id.tv_home);
                    tv_other = (TextView) view1.findViewById(R.id.tv_other);
                    tv_work = (TextView) view1.findViewById(R.id.tv_work);
                    tv_mobile.setText(phoneNum);

                    tv_home.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            address_type = "Home";
                            tv_home.setBackgroundResource(R.drawable.bg_textview_color);
                            tv_work.setBackgroundResource(R.drawable.textfield_bg);
                            tv_other.setBackgroundResource(R.drawable.textfield_bg);
                            // Toast.makeText(Address.this,address_type,Toast.LENGTH_SHORT).show();
                        }
                    });
                    tv_work.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            address_type = "Work";
                            tv_work.setBackgroundResource(R.drawable.bg_textview_color);
                            tv_other.setBackgroundResource(R.drawable.textfield_bg);
                            tv_home.setBackgroundResource(R.drawable.textfield_bg);
                            // Toast.makeText(Address.this,address_type,Toast.LENGTH_SHORT).show();
                        }
                    });
                    tv_other.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            address_type = "Other";
                            tv_other.setBackgroundResource(R.drawable.bg_textview_color);
                            tv_work.setBackgroundResource(R.drawable.textfield_bg);
                            tv_home.setBackgroundResource(R.drawable.textfield_bg);
                            //  Toast.makeText(Address.this,address_type,Toast.LENGTH_SHORT).show();
                        }
                    });

                    btn_save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (checkValidation()) {
                                //Toast.makeText(Address.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();
//                            if (BlankId.equals(loginPref.getString("device_id", ""))) {
//                                Toast.makeText(getApplicationContext(), "Please Login First", Toast.LENGTH_SHORT).show();
//                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                Account account = new Account();
//                                fragmentTransaction.replace(R.id.main_container, account);
//                                fragmentTransaction.addToBackStack(null).commit();
//                            } else {
                                Add_Address();
                                bottomSheetDialog.dismiss();
                                // }
                            }
                        }
                    });
                    bottomSheetDialog.setContentView(view1);
                    bottomSheetDialog.show();
                    bottomSheetDialog.setCanceledOnTouchOutside(false);
                }

            }
        });
    }

    private void getAllAddress() {
        progressDialog.show();
        Call<AllAddressListResponse> getAllAddress = ApiService.apiHolders().getAllAddress(id, token);
        getAllAddress.enqueue(new Callback<AllAddressListResponse>() {
            @Override
            public void onResponse(Call<AllAddressListResponse> call, Response<AllAddressListResponse> response) {
                progressDialog.dismiss();
                //  Toast.makeText(Address.this, "Successfully", Toast.LENGTH_SHORT).show();
                allAddressList = response.body().getData();
                txt_noData.setVisibility(View.GONE);
                rv_all_address.setVisibility(View.VISIBLE);
                addressListAdapter = new AddressListAdapter(getApplication(), allAddressList, new AddressListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(AllAddressListResponse.Datum item, int position, int type) {
                        int id = item.getAddress_id();
                        deleteAddress(id);
                    }

                    @Override
                    public void onItemClickEdit(AllAddressListResponse.Datum item, int position, int type) {
                        //  Toast.makeText(Address.this, "Hello", Toast.LENGTH_SHORT).show();
                        EditAddress(item, position);
                    }

                    @Override
                    public void onItemClickCheck(AllAddressListResponse.Datum item, int position, int type, boolean isChecked) {
                        if (addr_type == 1) {
                            add_Id = item.getAddress_id();
                            adds_one = item.getAddress_line_one();
                            adds_two = item.getAddress_line_two();
                            add_type = item.getAddress_type();
                            city = item.getCity();

                            editor1.putInt("add_id", add_Id);
                            editor1.putString("adds_one", adds_one);
                            editor1.putString("adds_two", adds_two);
                            editor1.putString("add_type", add_type);
                            editor1.putString("city", city);
                            editor1.commit();

                            startActivity(new Intent(Address.this, MyCart.class));
                        } else {
                            finish();
                        }
                    }
                });
                rv_all_address.setAdapter(addressListAdapter);
                rv_all_address.setLayoutManager(new LinearLayoutManager(getApplication()));

            }

            @Override
            public void onFailure(Call<AllAddressListResponse> call, Throwable t) {
//                Toast.makeText(Address.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(Address.this, "failed", Toast.LENGTH_SHORT).show();
                txt_noData.setVisibility(View.VISIBLE);
                rv_all_address.setVisibility(View.INVISIBLE);
                progressDialog.dismiss();
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
        tv_home = (TextView) view1.findViewById(R.id.tv_home);
        tv_other = (TextView) view1.findViewById(R.id.tv_other);
        tv_work = (TextView) view1.findViewById(R.id.tv_work);

        address_type = "";


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
        int address_id = item.getAddress_id();
        String addr_type = item.getAddress_type();

//        if (Objects.equals(addr_type, "Home")) {
//            tv_home.setBackgroundResource(R.drawable.bg_textview_color);
//            tv_work.setBackgroundResource(R.drawable.textfield_bg);
//            tv_other.setBackgroundResource(R.drawable.textfield_bg);
//        } else if (Objects.equals(addr_type, "Work")) {
//            tv_work.setBackgroundResource(R.drawable.bg_textview_color);
//            tv_other.setBackgroundResource(R.drawable.textfield_bg);
//            tv_home.setBackgroundResource(R.drawable.textfield_bg);
//        } else if (Objects.equals(addr_type, "Other")) {
//            tv_other.setBackgroundResource(R.drawable.bg_textview_color);
//            tv_work.setBackgroundResource(R.drawable.textfield_bg);
//            tv_home.setBackgroundResource(R.drawable.textfield_bg);
//        }

        tv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address_type = "Home";
                tv_home.setBackgroundResource(R.drawable.bg_textview_color);
                tv_work.setBackgroundResource(R.drawable.textfield_bg);
                tv_other.setBackgroundResource(R.drawable.textfield_bg);
                // Toast.makeText(Address.this,address_type,Toast.LENGTH_SHORT).show();
            }
        });
        tv_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address_type = "Work";
                tv_work.setBackgroundResource(R.drawable.bg_textview_color);
                tv_other.setBackgroundResource(R.drawable.textfield_bg);
                tv_home.setBackgroundResource(R.drawable.textfield_bg);
                // Toast.makeText(Address.this,address_type,Toast.LENGTH_SHORT).show();
            }
        });
        tv_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address_type = "Other";
                tv_other.setBackgroundResource(R.drawable.bg_textview_color);
                tv_work.setBackgroundResource(R.drawable.textfield_bg);
                tv_home.setBackgroundResource(R.drawable.textfield_bg);
                //  Toast.makeText(Address.this,address_type,Toast.LENGTH_SHORT).show();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    //Toast.makeText(Address.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();
                    UpdateAddress(address_id, addrs_one, addrs_second, landMark, city, phone, address_type);
                    bottomSheetDialog.dismiss();
                }
            }
        });
        bottomSheetDialog.setContentView(view1);
        bottomSheetDialog.show();
        bottomSheetDialog.setCanceledOnTouchOutside(false);
    }

    private void UpdateAddress(int address_id, String addrs_one, String addrs_second, String landMark, String city, String phone, String addr_type) {
        progressDialog.show();
        Call<EditAddressResponse> editAddress = ApiService.apiHolders().update_address(token, address_id, addrs_one, addrs_second, landMark, city, phone, addr_type);
        editAddress.enqueue(new Callback<EditAddressResponse>() {
            @Override
            public void onResponse(Call<EditAddressResponse> call, Response<EditAddressResponse> response) {
                progressDialog.dismiss();
                //Toast.makeText(Address.this, "Address updated Successfully", Toast.LENGTH_SHORT).show();
                Snackbar errorBar;
                errorBar = Snackbar.make(Rl_layout, "Address updated Successfully", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();

                getAllAddress();
            }

            @Override
            public void onFailure(Call<EditAddressResponse> call, Throwable t) {
                //Toast.makeText(Address.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(Address.this, "failed", Toast.LENGTH_SHORT).show();
                Snackbar errorBar;
                errorBar = Snackbar.make(Rl_layout, "failed", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();


                progressDialog.dismiss();
            }
        });
    }


    private void deleteAddress(int id) {
        progressDialog.show();
        Call<DeleteResponse> addAddress = ApiService.apiHolders().deleteAddress(id, token);
        addAddress.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                progressDialog.dismiss();
                Snackbar errorBar;
                errorBar = Snackbar.make(Rl_layout, "Address Deleted Successfully", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();

                SharedPreferences preferences = getSharedPreferences("address_pref", Context.MODE_PRIVATE);
                editor1 = preferences.edit();
                editor1.clear();
                editor1.apply();

                // Toast.makeText(Address.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();


                getAllAddress();
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
//                Toast.makeText(Address.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                //  Toast.makeText(Address.this, "failed", Toast.LENGTH_SHORT).show();
                Snackbar errorBar;
                errorBar = Snackbar.make(Rl_layout, "failed", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();
                progressDialog.dismiss();
            }
        });
    }

    private void Add_Address() {
        String area = tv_area.getText().toString();
        String building = tv_building.getText().toString();
        String landmark = tv_landmark.getText().toString();
        String city = tv_city.getText().toString();
        progressDialog.show();

        Call<AddAddressResponse> addAddress = ApiService.apiHolders().add_address(id, area, building, landmark, city,
                phoneNum, address_type, token);
        addAddress.enqueue(new Callback<AddAddressResponse>() {
            @Override
            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {
                progressDialog.dismiss();
                // Toast.makeText(Address.this, "Address Added Successfully", Toast.LENGTH_SHORT).show();

                Snackbar errorBar;
                errorBar = Snackbar.make(Rl_layout, "Address Added Successfully", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();

                getAllAddress();
            }

            @Override
            public void onFailure(Call<AddAddressResponse> call, Throwable t) {
                //Toast.makeText(Address.this, "failed", Toast.LENGTH_SHORT).show();
                Snackbar errorBar;
                errorBar = Snackbar.make(Rl_layout, "failed", Snackbar.LENGTH_LONG);
                errorBar.setTextColor(getResources().getColor(R.color.white));
                errorBar.setActionTextColor(getResources().getColor(R.color.white));
                errorBar.setBackgroundTint(getResources().getColor(R.color.error));
                errorBar.show();
                //    progressDialog.dismiss();
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
        } else if (address_type.equals("")) {
            Toast.makeText(Address.this, "Please Select Address Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}