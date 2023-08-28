package com.example.licious.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RepeatOrderResponse {
    @SerializedName("data")
    @Expose
    private List<Datum> data;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("qty")
        @Expose
        private String qty;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("product_title")
        @Expose
        private String productTitle;
        @SerializedName("weight")
        @Expose
        private Integer weight;
        @SerializedName("weight_type")
        @Expose
        private String weightType;
        @SerializedName("mrp")
        @Expose
        private Integer mrp;
        @SerializedName("offer_price")
        @Expose
        private Integer offerPrice;
        @SerializedName("product_image")
        @Expose
        private String productImage;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getProductTitle() {
            return productTitle;
        }

        public void setProductTitle(String productTitle) {
            this.productTitle = productTitle;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getWeightType() {
            return weightType;
        }

        public void setWeightType(String weightType) {
            this.weightType = weightType;
        }

        public Integer getMrp() {
            return mrp;
        }

        public void setMrp(Integer mrp) {
            this.mrp = mrp;
        }

        public Integer getOfferPrice() {
            return offerPrice;
        }

        public void setOfferPrice(Integer offerPrice) {
            this.offerPrice = offerPrice;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }
    }
}
