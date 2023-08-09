package com.example.licious.api;

import com.example.licious.response.AddAddressResponse;
import com.example.licious.response.AddToCartResponse;
import com.example.licious.response.AddWishListResponse;
import com.example.licious.response.AllAddressListResponse;
import com.example.licious.response.AllWishListResponse;
import com.example.licious.response.BannerResponse;
import com.example.licious.response.Best_Seller_Response;
import com.example.licious.response.CartDetailsResponse;
import com.example.licious.response.Category_Response;
import com.example.licious.response.DeleteResponse;
import com.example.licious.response.EditAddressResponse;
import com.example.licious.response.ImageResponse;
import com.example.licious.response.Master_Category_Response;
import com.example.licious.response.Otp_verify_Response;
import com.example.licious.response.Pages_Response;
import com.example.licious.response.ProfileResponse;
import com.example.licious.response.SendOtp_Response;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
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

    @POST("addnewaddress")
    @FormUrlEncoded
    Call<AddAddressResponse> add_address(@Field("user_id") int user_id,
                                         @Field("address_line_one") String address_line_one,
                                         @Field("address_line_two") String address_line_two,
                                         @Field("landmark") String landmark,
                                         @Field("city") String city,
                                         @Field("mobile_number") String mobile_number,
                                         @Field("address_type") String address_type,
                                         @Field("token") String token);

    @GET("getaddress")
    Call<AllAddressListResponse> getAllAddress(@Query("user_id") int user_id,
                                               @Query("token") String token);

    @GET("getwishlist")
    Call<AllWishListResponse> getAllWishList(@Query("user_id") int user_id,
                                             @Query("token") String token);

    @DELETE("deleteaddress")
    Call<DeleteResponse> deleteAddress(@Query("address_id") int address_id,
                                       @Query("token") String token);

    @PATCH("updateaddress")
    @FormUrlEncoded
    Call<EditAddressResponse> update_address(@Query("token") String token,
                                             @Field("address_id") int address_id,
                                             @Field("address_line_one") String address_line_one,
                                             @Field("address_line_two") String address_line_two,
                                             @Field("landmark") String landmark,
                                             @Field("city") String city,
                                             @Field("mobile_number") String mobile_number,
                                             @Field("address_type") String address_type);

    @POST("addwishlist")
    @FormUrlEncoded
    Call<AddWishListResponse> add_wishList(@Field("user_id") int user_id,
                                           @Field("product_id") int product_id,
                                           @Field("status") String status,
                                           @Field("token") String token);

    @GET("cproduct")
    Call<Category_Response> getCategory(@Query("cId") int cId,
                                        @Query("token") String token);

    @POST("addtocart")
    @FormUrlEncoded
    Call<AddToCartResponse> add_to_cart(@Field("user_id") int user_id,
                                        @Field("product_id") int product_id,
                                        @Field("token") String token);

    @GET("cproduct")
    Call<CartDetailsResponse> getCartDetails(@Query("user_id") int user_id,
                                             @Query("token") String token);


}
