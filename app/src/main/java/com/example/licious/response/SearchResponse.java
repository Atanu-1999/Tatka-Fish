package com.example.licious.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

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
        @SerializedName("mc_id")
        @Expose
        private Integer mcId;
        @SerializedName("c_id")
        @Expose
        private Integer cId;
        @SerializedName("sc_id")
        @Expose
        private Integer scId;
        @SerializedName("product_title")
        @Expose
        private String productTitle;
        @SerializedName("product_image")
        @Expose
        private String productImage;
        @SerializedName("short_description")
        @Expose
        private String shortDescription;
        @SerializedName("weight")
        @Expose
        private Integer weight;
        @SerializedName("weight_type")
        @Expose
        private String weightType;
        @SerializedName("pieces")
        @Expose
        private String pieces;
        @SerializedName("serves")
        @Expose
        private String serves;
        @SerializedName("mrp")
        @Expose
        private String mrp;
        @SerializedName("offer")
        @Expose
        private Integer offer;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("product_type")
        @Expose
        private String productType;
        @SerializedName("best_seller")
        @Expose
        private String bestSeller;
        @SerializedName("top_rated")
        @Expose
        private String topRated;
        @SerializedName("recommended")
        @Expose
        private String recommended;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("wishlist_status")
        @Expose
        private String wishlistStatus;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getMcId() {
            return mcId;
        }

        public void setMcId(Integer mcId) {
            this.mcId = mcId;
        }

        public Integer getcId() {
            return cId;
        }

        public void setcId(Integer cId) {
            this.cId = cId;
        }

        public Integer getScId() {
            return scId;
        }

        public void setScId(Integer scId) {
            this.scId = scId;
        }

        public String getProductTitle() {
            return productTitle;
        }

        public void setProductTitle(String productTitle) {
            this.productTitle = productTitle;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
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

        public String getPieces() {
            return pieces;
        }

        public void setPieces(String pieces) {
            this.pieces = pieces;
        }

        public String getServes() {
            return serves;
        }

        public void setServes(String serves) {
            this.serves = serves;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public Integer getOffer() {
            return offer;
        }

        public void setOffer(Integer offer) {
            this.offer = offer;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getBestSeller() {
            return bestSeller;
        }

        public void setBestSeller(String bestSeller) {
            this.bestSeller = bestSeller;
        }

        public String getTopRated() {
            return topRated;
        }

        public void setTopRated(String topRated) {
            this.topRated = topRated;
        }

        public String getRecommended() {
            return recommended;
        }

        public void setRecommended(String recommended) {
            this.recommended = recommended;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getWishlistStatus() {
            return wishlistStatus;
        }

        public void setWishlistStatus(String wishlistStatus) {
            this.wishlistStatus = wishlistStatus;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }
}
