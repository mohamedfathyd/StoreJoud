package com.khalej.storejoud.model;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface apiinterface_home {


    @FormUrlEncoded
    @POST("api/login")
    Call<contact_general_user> getcontacts_login(@Field("phone") String kayWord, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/contacts")
    Call<ResponseBody> CallUs(@Field("name") String name, @Field("email") String address, @Field("phone") String phone,
                              @Field("message") String message);

    @FormUrlEncoded
    @POST("api/request-shipments")
    Call<ResponseBody> confirmShipment(@HeaderMap Map<String, String> headers, @Field("description") String description, @Field("address_id") String address_id);

    @FormUrlEncoded
    @POST("api/forget/password")
    Call<Reset>getcontacts_ResetPassword(@Field("email") String kayWord);


    @FormUrlEncoded
    @POST("api/forget/password/new")
    Call<Reset>getcontacts_updatePassword(@Field("email") String kayWord, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/forget/password/reset")
    Call<Reset>getcontacts_tokenPassword(@Field("email") String kayWord, @Field("token") String password);

    @GET("api/banners")
    Call<contact_general> getcontacts_generalData();

    @GET("api/categories")
    Call<contact_category> getcontacts_category(@Query("lang") String lang);

    @GET("api/products")
    Call<contact_products> getcontacts_products(@Query("lang") String lang);

    @GET("api/products/offers")
    Call<contact_products> getcontacts_products_offer(@Query("lang") String lang);

    @GET("api/products/top-selling")
    Call<contact_products> getcontacts_products_top(@Query("lang") String lang);



    @GET("api/products/top-rated")
    Call<contact_products> getcontacts_products_rated(@Query("lang") String lang);

    @GET("api/orders/notifications")
    Call<contact_notifications> getcontacts_notifications(@HeaderMap Map<String, String> headers);

    @GET("api/customer/profile/orders/active")
    Call<contact_myOrders> getcontacts_myOrders(@HeaderMap Map<String, String> headers);


    @Multipart
    @POST("api/profile/update/logo")
    Call<contact_profile>getcontact_updateImage(@Part MultipartBody.Part image,@HeaderMap Map<String, String> headers);

    @GET("api/products/favorites")
    Call<contact_products> getcontacts_products_favorites(@HeaderMap Map<String, String> headers,@Query("lang") String lang);

    @GET("api/products/{product}")
    Call<contact_single_product> getcontacts_productDetailsById(@Path("product") String product);

    @DELETE("api/products/{id}/mark-as-un-favorite")
    Call<ResponseBody> delete_favourit(@HeaderMap Map<String, String> headers, @Path("id") String id);

    @POST("api/products/{product}/cart")
    Call<ResponseBody> getcontacts_addtoCart(@HeaderMap Map<String, String> headers,@Path("product") String product);

    @POST("api/products/{product}/mark-as-favorite")
    Call<ResponseBody> getcontacts_markasFavourit(@HeaderMap Map<String, String> headers,@Path("product") String product);

    @FormUrlEncoded
    @POST("api/products/{product}/submit-review")
    Call<ResponseBody> getcontacts_addreview(@HeaderMap Map<String, String> headers,@Path("product") String product,
                                             @Field("review") String review,@Field("rate") int rate);


    @FormUrlEncoded
    @POST("api/user/addresses")
    Call<ResponseBody> getcontacts_addaddress(@HeaderMap Map<String, String> headers,
                                             @Field("name") String name,@Field("phone") String phone,
                                              @Field("street_name") String street_name,@Field("city") String city,
                                              @Field("postal_code") String postal_code);


    @GET("api/categories/{id}/products")
    Call<contact_products> getcontacts_productsById(@Path("id") String id);

    @GET("api/products/{id}/reviews")
    Call<contact_reviews> getcontacts_reviews(@HeaderMap Map<String, String> headers,@Path("id") String id);

    @GET("api/user/addresses")
    Call<contact_address> getcontacts_address(@HeaderMap Map<String, String> headers);


    @GET("api/search")
    Call<contact_products> getcontacts_search(@Query("search_with_key") String search_with_key);

    @GET("api/cities")
    Call<contact_cities> getcontacts_cities();

    @GET("api/cart")
    Call<contact_cart> getcontacts_cart(@HeaderMap Map<String, String> headers,@Query("lang") String lang);

    @FormUrlEncoded
    @POST("api/feedback")
    Call<ResponseBody> getcontacts_AddRate(@Field("rate") String rate,
                                           @Field("comment") String des);

    @FormUrlEncoded
    @POST("api/promo-codes/check")
    Call<contact_copon> getcontacts_copon(@HeaderMap Map<String, String> headers,
                                           @Field("promo_code") String promo_code);






    @FormUrlEncoded
    @POST("api/products/order")
    Call<ResponseBody> content_addOrder(@HeaderMap Map<String, String> headers, @Field("address_id") String address_id,
                                        @Field("day") String day, @Field("time") String time,
                                        @Field("payment_method") String payment_method, @Field("comment") String comment
    );

    @FormUrlEncoded
    @POST("api/register")
    Call<contact_general_user> getcontacts_newaccount(@Field("full_name") String name, @Field("password") String password, @Field("email") String address,
                                                      @Field("phone") String phone);

    @FormUrlEncoded
    @PATCH("api/profile/update")
    Call <contact_general_user_update> updateProfile(@HeaderMap Map<String, String> headers, @Field("full_name") String full_name,
                                                     @Field("email") String email, @Field("phone") String phone);

    @FormUrlEncoded
    @PATCH("api/profile/update/password")
    Call <contact_general_user_update> updateProfile_pass(@HeaderMap Map<String, String> headers,
                                                          @Field("password") String password,
                                                          @Field("password_confirmation") String password_confirmation);

    @FormUrlEncoded
    @PATCH("api/cart/{product_id}/update")
    Call <ResponseBody> updatecart(@HeaderMap Map<String, String> headers,
                                                          @Path("product_id") String product_id,
                                                          @Field("update_quantity") String update_quantity);

    @FormUrlEncoded
    @POST("api/products/order")
    Call <ResponseBody> addOrder(@HeaderMap Map<String, String> headers,
                                   @Field("address_id") String address_id,@Field("promo_code") String promo_code,
                                   @Field("payment_method") String payment_method);


    @FormUrlEncoded
    @POST("api/products/{product_id}/cart")
    Call<ResponseBody> content_addcard(@HeaderMap Map<String, String> headers, @Field("quantity") int quantity,
                                       @Field("product_id") String product_id);


    @DELETE("api/user/addresses/destroy/{address_id}")
    Call<ResponseBody> delete_address(@HeaderMap Map<String, String> headers, @Path("address_id") String address_id);

    @DELETE("api/cart/destroy/{product_id}")
    Call<ResponseBody> delete_cart(@HeaderMap Map<String, String> headers, @Path("product_id") String product_id);

    @FormUrlEncoded
    @POST("api/user/addresses")
    Call<ResponseBody> content_addaddress(@HeaderMap Map<String, String> headers, @Field("flat_number") String flat_number,
                                          @Field("block_number") String block_number, @Field("floor_number") String floor_number,
                                          @Field("street_name") String street_name, @Field("type") String type,
                                          @Field("lat") double lat, @Field("lng") double lng
    );


    @PATCH("/api/orders/{order_id}/mark-as-in-shipping")
    Call<ResponseBody> markShipping(@HeaderMap Map<String, String> headers, @Path("order_id") String order_id);

    @PATCH("/api/orders/{order_id}/mark-as-not-in-shipping")
    Call<ResponseBody> marknotShipping(@HeaderMap Map<String, String> headers, @Path("order_id") String order_id);

    @PATCH("/api/orders/{order_id}/mark-as-delivered")
    Call<ResponseBody> markdelevried(@HeaderMap Map<String, String> headers, @Path("order_id") String order_id);

    @GET("api/general")
    Call<contact_general_> getcontacts_g(@Query("lang") String lang);


    @Multipart
    @POST("api/register")
    Call<contact_general_user>getcontact_newaccount(@Part MultipartBody.Part image, @Part("first_name") RequestBody first_name,
                                                    @Part("last_name") RequestBody last_name, @Part("email") RequestBody email,
                                                    @Part("password") RequestBody password, @Part("phone") RequestBody phone,
                                                    @Part("national_id") RequestBody national_id,
                                                    @Part("national_id_expiry_date") RequestBody national_id_expiry_date);



    @DELETE("/api/profile/addresses/{id}/delete")
    Call<ResponseBody> deleteAddress(@HeaderMap Map<String, String> headers, @Path("id") String id);


    @FormUrlEncoded
    @POST("api/profile/addresses")
    Call<ResponseBody> add_newAddress(@HeaderMap Map<String, String> headers, @Field("address") String address,
                                      @Field("building_number") int building_number, @Field("floor") int floor,
                                      @Field("flat_number") int flat_number,
                                      @Field("additional_info") String additional_info, @Field("landline_number") String landline_number,
                                      @Field("type") String type, @Field("is_primary") int is_primary,
                                      @Field("latitude") double latitude, @Field("longitude") double longitude,
                                      @Field("city_id") String city_id);


    @FormUrlEncoded
    @PATCH("api/shipments/tasks/mark-as-received")
    Call<ResponseBody> markreceived(@HeaderMap Map<String, String> headers, @Field("track_code") String order_id);

    @FormUrlEncoded
    @PATCH("api/profile/user-token/update")
    Call<ResponseBody> updateToken(@HeaderMap Map<String, String> headers, @Field("user_token") String user_token);

    @FormUrlEncoded
    @PATCH("api/shipments/mark-as-delivered")
    Call<ResponseBody> markdelevir(@HeaderMap Map<String, String> headers, @Field("track_code") String order_id);

    @FormUrlEncoded
    @PATCH("api/shipments/mark-as-canceled")
    Call<ResponseBody> markcancel(@HeaderMap Map<String, String> headers, @Field("track_code") String order_id,
                                  @Field("driver_comment") String driver_comment);

    @FormUrlEncoded
    @POST("api/shipments/tasks/assign-to-driver")
    Call<ResponseBody> content_assign(@HeaderMap Map<String, String> headers, @Field("track_code") String track_code);
}

