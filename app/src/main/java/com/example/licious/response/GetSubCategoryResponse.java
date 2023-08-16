package com.example.licious.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetSubCategoryResponse {
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
        private Integer mc_id;
        @SerializedName("c_id")
        @Expose
        private Integer c_id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("wishlist_status")
        @Expose
        private String wishlist_status;

        public String getWishlist_status() {
            return wishlist_status;
        }

        public void setWishlist_status(String wishlist_status) {
            this.wishlist_status = wishlist_status;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getMc_id() {
            return mc_id;
        }

        public void setMc_id(Integer mc_id) {
            this.mc_id = mc_id;
        }

        public Integer getC_id() {
            return c_id;
        }

        public void setC_id(Integer c_id) {
            this.c_id = c_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }
}
