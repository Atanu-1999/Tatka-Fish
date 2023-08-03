package com.example.licious.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.licious.R;
import com.example.licious.activity.MyCart;
import com.example.licious.adapter.Best_Seller_Adapter;
import com.example.licious.adapter.Slider_Adapter;
import com.example.licious.adapter.Top_Rated_Adapter;
import com.example.licious.api.ApiService;
import com.example.licious.authentication.AddressUtils;
import com.example.licious.authentication.DeviceUtils;
import com.example.licious.model.Slider_Model;
import com.example.licious.response.Best_Seller_Response;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends Fragment {

    ViewPager viewPager;
    Slider_Adapter slider_adapter;
    List<Slider_Model> modelList;
    CardView fish_layout;
    LinearLayout product_one;
    ImageView btn_cart, fav_image, btn_add;
    TextView text_wish_time, txt_address_set;
    private Handler handler;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private LocationManager locationManager;
    private LocationListener locationListener;

   /* private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private LocationManager locationManager;
    private MyLocationListener locationListener;*/

    List<Best_Seller_Response.Datum> best_seller_response;
    Best_Seller_Adapter best_seller_adapter;
    RecyclerView rv_bestSeller,rv_topRated;

    Top_Rated_Adapter top_rated_adapter;

    public Home() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View home = inflater.inflate(R.layout.fragment_home, container, false);
        /*Device Id Get*/
        String deviceId = DeviceUtils.getDeviceId(getContext());
        Log.e("Device Id", "" + deviceId);
        /*Initialization*/
        txt_address_set = home.findViewById(R.id.txt_address_set);
        text_wish_time = home.findViewById(R.id.text_wish_time);
//        btn_add = home.findViewById(R.id.btn_add);
 //       fav_image = home.findViewById(R.id.fav_image);
        btn_cart = home.findViewById(R.id.btn_cart);
 //       product_one = home.findViewById(R.id.product_one);
        rv_bestSeller = home.findViewById(R.id.rv_bestSeller);
        rv_topRated = home.findViewById(R.id.rv_topRated);

        /*Functionality*/
        handler = new Handler();
        modelList = new ArrayList<>();
        modelList.add(new Slider_Model(R.drawable.onboard_one));
        modelList.add(new Slider_Model(R.drawable.onboard_three));
        modelList.add(new Slider_Model(R.drawable.banner_one));
        modelList.add(new Slider_Model(R.drawable.onboard_two));
        modelList.add(new Slider_Model(R.drawable.banner_two));

        slider_adapter = new Slider_Adapter(modelList, getContext());
        viewPager = home.findViewById(R.id.view_pager);
        viewPager.setAdapter(slider_adapter);
        viewPager.setPadding(80, 0, 80, 0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        /*Location Permission*/
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission is granted, start requesting location updates
            startLocationUpdates();
        }
        /*Method calling*/
        updateGreeting();
//        product_one.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), ProductDetails.class));
//            }
//        });
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MyCart.class));
            }
        });
//        fav_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), Wishlist.class));
//            }
//        });
//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), MyCart.class));
//            }
//        });

        //Best Seller
        BestSeller("abcdefgh12345");
        //Top Rated
        TopRated("abcdefgh12345");



        return home;
    }

    private void TopRated(String token) {
        Call<Best_Seller_Response> login_apiCall = ApiService.apiHolders().topRated(token);
        login_apiCall.enqueue(new Callback<Best_Seller_Response>() {
            @Override
            public void onResponse(Call<Best_Seller_Response> call, Response<Best_Seller_Response> response) {
                if (response.isSuccessful()) {
                    String response1 = response.body().toString();
                    best_seller_response = response.body().getData();
                    top_rated_adapter = new Top_Rated_Adapter(getContext(), best_seller_response);
                    rv_topRated.setAdapter(top_rated_adapter);
                    // rv_bestSeller.setLayoutManager(new LinearLayoutManager(getContext()));
                    rv_topRated.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                } else {

                }
            }

            @Override
            public void onFailure(Call<Best_Seller_Response> call, Throwable t) {

            }
        });
    }

    private void BestSeller(String token) {
        Call<Best_Seller_Response> login_apiCall = ApiService.apiHolders().bestSeller(token);
        login_apiCall.enqueue(new Callback<Best_Seller_Response>() {
            @Override
            public void onResponse(Call<Best_Seller_Response> call, Response<Best_Seller_Response> response) {
                if (response.isSuccessful()) {
                    String response1 = response.body().toString();
                    best_seller_response = response.body().getData();
                    best_seller_adapter = new Best_Seller_Adapter(getContext(), best_seller_response);
                    rv_bestSeller.setAdapter(best_seller_adapter);
                    // rv_bestSeller.setLayoutManager(new LinearLayoutManager(getContext()));
                    rv_bestSeller.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                } else {

                }
            }

            @Override
            public void onFailure(Call<Best_Seller_Response> call, Throwable t) {

            }
        });
    }

    private void startLocationUpdates() {
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();

        // Request location updates with a desired time interval and distance change
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0, 0, locationListener);
    }

    private void updateLocationTextView(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            String address = AddressUtils.getAddress(getContext(), latitude, longitude);

            txt_address_set.setText(address);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted, start requesting location updates
                startLocationUpdates();
            } else {
                // Location permission denied, handle accordingly
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            updateLocationTextView(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LocationListener.super.onStatusChanged(provider, status, extras);
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            LocationListener.super.onProviderEnabled(provider);
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }
    }

    private void updateGreeting() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String greeting;
        if (hour >= 6 && hour < 12) {
            greeting = "Good Morning";
        } else if (hour >= 12 && hour < 18) {
            greeting = "Good Afternoon";
        } else {
            greeting = "Good Evening";
        }
        text_wish_time.setText(greeting);
    }
}