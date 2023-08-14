package com.example.licious.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {
    @SerializedName("data")
    @Expose
    private List<Datum> data;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;

    public class Datum {
//        @SerializedName("id")
//        @Expose
//        private Integer id;
//        @SerializedName("mc_id")
//        @Expose
//        private Integer mc_id;
//        @SerializedName("c_id")
//        @Expose
//        private Integer c_id;
//        @SerializedName("sc_id")
//        @Expose
//        private String sc_id;
//        @SerializedName("product_title")
//        @Expose
//        private String product_title;
//        @SerializedName("product_image")
//        @Expose
//        private String product_image;
//        @SerializedName("short_description")
//        @Expose
//        private String short_description;
//        @SerializedName("weight")
//        @Expose
//        private String weight;
//        @SerializedName("weight_type")
//        @Expose
//        private String weight_type;
//        @SerializedName("pieces")
//        @Expose
//        private String pieces;
//        @SerializedName("serves")
//        @Expose
//        private String serves;
//        @SerializedName("mrp")
//        @Expose
//        private String mrp;
//        @SerializedName("offer")
//        @Expose
//        private String offer;
//        @SerializedName("price")
//        @Expose
//        private String price;
//
//        @SerializedName("description")
//        @Expose
//        private String description;
//        @SerializedName("product_type")
//        @Expose
//        private String product_type;
//        @SerializedName("best_seller")
//        @Expose
//        private String best_seller;
//
//        @SerializedName("top_rated")
//        @Expose
//        private String top_rated;
//        @SerializedName("recommended")
//        @Expose
//        private String recommended;
//        @SerializedName("status")
//        @Expose
//        private String status;
//        @SerializedName("createdAt")
//        @Expose
//        private String createdAt;
    }
}
