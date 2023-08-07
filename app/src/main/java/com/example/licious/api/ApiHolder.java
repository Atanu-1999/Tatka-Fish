package com.example.licious.api;

import com.example.licious.response.BannerResponse;
import com.example.licious.response.Best_Seller_Response;
import com.example.licious.response.ImageResponse;
import com.example.licious.response.Master_Category_Response;
import com.example.licious.response.Otp_verify_Response;
import com.example.licious.response.Pages_Response;
import com.example.licious.response.ProfileResponse;
import com.example.licious.response.SendOtp_Response;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("pages")
    Call<Pages_Response> pages(@Query("pageId") String pageId,
                               @Query("token") String token);

    @GET("banners")
    Call<BannerResponse> banner(@Query("token") String token);

    @PATCH("updateProfile")
    @FormUrlEncoded
    Call<ProfileResponse> UpdateProfile(@Query("token") String token,
                                        @Field("userId") int userId,
                                        @Field("first_name") String first_name,
                                        @Field("last_name") String last_name,
                                        @Field("email") String email,
                                        @Field("phone") String phone,
                                        @Field("dob") String dob,
                                        @Field("gender") String gender);

    @Multipart
    @POST("updateimg")
    Call<ImageResponse> profile_change(@Part("userId") RequestBody userId,
                                       @Part("token") RequestBody token,
                                       @Part MultipartBody.Part userImg);


}
