package com.example.licious.api;

import com.example.licious.response.Best_Seller_Response;
import com.example.licious.response.Master_Category_Response;
import com.example.licious.response.Otp_verify_Response;
import com.example.licious.response.SendOtp_Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiHolder {

    @POST("sendotp")
    @FormUrlEncoded
    Call<SendOtp_Response> sent_otp(@Field("phone") String phone,
                                     @Field("device_id") String device_id);
    @POST("verifyotp")
    @FormUrlEncoded
    Call<Otp_verify_Response> otp_verify(@Field("phone") String phone,
                                       @Field("device_id") String device_id,
                                         @Field("otp") String otp);

    @GET("mastercategory")
    Call<Master_Category_Response> category(@Query("token") String token);

    @GET("bsproduct")
    Call<Best_Seller_Response> bestSeller(@Query("token") String token);

    @GET("trproduct")
    Call<Best_Seller_Response> topRated(@Query("token") String token);


}
