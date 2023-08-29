package com.example.licious.api;

import com.example.licious.response.AddAddressResponse;
import com.example.licious.response.AddRemoveResponse;
import com.example.licious.response.AddToCartResponse;
import com.example.licious.response.AddWishListResponse;
import com.example.licious.response.AllAddressListResponse;
import com.example.licious.response.AllCaterogyResponse;
import com.example.licious.response.AllWishListResponse;
import com.example.licious.response.BannerResponse;
import com.example.licious.response.Best_Seller_Response;
import com.example.licious.response.CartDetailsResponse;
import com.example.licious.response.CartItemDeleteResponse;
import com.example.licious.response.Category_Response;
import com.example.licious.response.CheckOutProccedResponse;
import com.example.licious.response.CouponsResponse;
import com.example.licious.response.DeleteResponse;
import com.example.licious.response.EditAddressResponse;
import com.example.licious.response.FaqResponse;
import com.example.licious.response.GetCategoryResponse;
import com.example.licious.response.GetMasterCategoryResponse;
import com.example.licious.response.GetSubCategoryResponse;
import com.example.licious.response.HistoryResponse;
import com.example.licious.response.ImageResponse;
import com.example.licious.response.NotificationListResponse;
import com.example.licious.response.OrderHistoryDataResponse;
import com.example.licious.response.Otp_verify_Response;
import com.example.licious.response.Pages_Response;
import com.example.licious.response.ProductResponse;
import com.example.licious.response.ProfileResponse;
import com.example.licious.response.RatingResponse;
import com.example.licious.response.RemoveWishListResponse;
import com.example.licious.response.RepeatOrderResponse;
import com.example.licious.response.RepeatResponse;
import com.example.licious.response.SearchResponse;
import com.example.licious.response.SendOtp_Response;
import com.example.licious.response.SlotResponse;
import com.example.licious.response.SubCategoryItemResponse;

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

    @POST("resendotp")
    @FormUrlEncoded
    Call<RepeatResponse> re_sent_otp(@Field("phone") String phone,
                                  @Field("device_id") String device_id);

    @POST("verifyotp")
    @FormUrlEncoded
    Call<Otp_verify_Response> otp_verify(@Field("phone") String phone,
                                         @Field("device_id") String device_id,
                                         @Field("otp") String otp,
                                         @Field("fb_token") String fb_token);

    @GET("mastercategory")
    Call<GetMasterCategoryResponse> category(@Query("token") String token);

    @GET("bsproduct")
    Call<Best_Seller_Response> bestSeller(@Query("token") String token);

    @GET("trproduct")
    Call<Best_Seller_Response> topRated(@Query("token") String token);

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

    @POST("addwishlist")
    @FormUrlEncoded
    Call<RemoveWishListResponse> remove_wishList(@Field("user_id") int user_id,
                                                 @Field("product_id") int product_id,
                                                 @Field("status") String status,
                                                 @Field("token") String token);

    @GET("category")
    Call<GetCategoryResponse> getCategory(@Query("mcId") int mcId,
                                          @Query("token") String token);

    @POST("addtocart")
    @FormUrlEncoded
    Call<AddToCartResponse> add_to_cart(@Field("user_id") int user_id,
                                        @Field("product_id") int product_id,
                                        @Field("price") String price,
                                        @Field("token") String token);

    @GET("getcartdata")
    Call<CartDetailsResponse> getCartDetails(@Query("user_id") int user_id,
                                             @Query("token") String token);

    @DELETE("deletecartdata")
    Call<CartItemDeleteResponse> deleteCartItem(@Query("cart_id") int user_id,
                                                @Query("token") String token);

    @PATCH("updatecart")
    @FormUrlEncoded
    Call<AddRemoveResponse> updatedCart(@Query("token") String token,
                                        @Field("cart_id") int cart_id,
                                        @Field("price") String price,
                                        @Field("qty") int qty);

    @GET("coupons")
    Call<CouponsResponse> getCoupons(@Query("token") String token);

    @GET("subcategory")
    Call<GetSubCategoryResponse> getSubCategory(@Query("cId") int cId,
                                                @Query("token") String token);

    @GET("slots")
    Call<SlotResponse> getTimeSlot(@Query("token") String token);

    @GET("cproduct")
    Call<SubCategoryItemResponse> getSubCategoryProduct(@Query("cId") int cId,
                                                        @Query("token") String token);

    @GET("rproduct")
    Call<SubCategoryItemResponse> getRecommendedProduct(@Query("token") String token);

    @GET("mcproduct")
    Call<Category_Response> getCategoryProduct(@Query("mcId") int mcId,
                                               @Query("token") String token);

    @GET("productdtls")
    Call<ProductResponse> getProductDetails(@Query("product_id") int product_id,
                                            @Query("token") String token);

    @GET("allcategory")
    Call<AllCaterogyResponse> allCategory(@Query("token") String token);

    @GET("getorderdata")
    Call<HistoryResponse> getOrderHistory(@Query("user_id") int user_id,
                                          @Query("token") String token);

    @GET("getorderproductdata")
    Call<OrderHistoryDataResponse> getOrderHistoryData(@Query("order_id") int order_id,
                                                       @Query("token") String token);

    @POST("addorder")
    @FormUrlEncoded
    Call<CheckOutProccedResponse> procedToCheckOut(@Field("user_id") int user_id,
                                                   @Field("sub_total") int sub_total,
                                                   @Field("delivery_charge") int delivery_charge,
                                                   @Field("total") int total,
                                                   @Field("delivery_date") String delivery_date,
                                                   @Field("slot_id") int slot_id,
                                                   @Field("address_id") int address_id,
                                                   @Field("token") String token);

    @GET("productsearch")
    Call<SearchResponse> getSearch(@Query("search") String search,
                                   @Query("token") String token);

    @GET("pages")
    Call<Pages_Response> getPage(@Query("pageId") String pageId,
                                 @Query("token") String token);

    @GET("notifications")
    Call<NotificationListResponse> getNotification(@Query("user_id") int user_id,
                                                   @Query("token") String token);


    @POST("addfeedback")
    @FormUrlEncoded
    Call<RatingResponse> ratingfeedback(@Field("user_id") int user_id,
                                        @Field("order_no") int order_no,
                                        @Field("star") String star,
                                        @Field("description") String description,
                                        @Field("token") String token);

    @POST("reorder")
    @FormUrlEncoded
    Call<RepeatOrderResponse> getReOrder(@Field("user_id") int user_id,
                                         @Field("order_id") int order_id,
                                         @Field("token") String token);

    @GET("faqs")
    Call<FaqResponse> getFaq(@Query("token") String token);


}
