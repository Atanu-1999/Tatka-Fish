package com.example.licious.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderHistoryResponse {
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
        @SerializedName("order")
        @Expose
        private List<Order> order;
        @SerializedName("orderdetails")
        @Expose
        private List<Orderdetails> orderdetails;

        public List<Order> getOrder() {
            return order;
        }

        public void setOrder(List<Order> order) {
            this.order = order;
        }

        public List<Orderdetails> getOrderdetails() {
            return orderdetails;
        }

        public void setOrderdetails(List<Orderdetails> orderdetails) {
            this.orderdetails = orderdetails;
        }

        public class Order{
            @SerializedName("id")
            @Expose
            private int id;
            @SerializedName("order_no")
            @Expose
            private String order_no;
            @SerializedName("order_date")
            @Expose
            private String order_date;
            @SerializedName("slot_id")
            @Expose
            private int slot_id;
            @SerializedName("status")
            @Expose
            private String status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public String getOrder_date() {
                return order_date;
            }

            public void setOrder_date(String order_date) {
                this.order_date = order_date;
            }

            public int getSlot_id() {
                return slot_id;
            }

            public void setSlot_id(int slot_id) {
                this.slot_id = slot_id;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
        public class Orderdetails{
            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("mc_id")
            @Expose
            private Integer mc_id;
            @SerializedName("c_id")
            @Expose
            private Integer c_id;
            @SerializedName("sc_id")
            @Expose
            private String sc_id;
            @SerializedName("product_title")
            @Expose
            private String product_title;
            @SerializedName("product_image")
            @Expose
            private String product_image;
            @SerializedName("short_description")
            @Expose
            private String short_description;
            @SerializedName("weight")
            @Expose
            private String weight;
            @SerializedName("weight_type")
            @Expose
            private String weight_type;
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
            private String offer;
            @SerializedName("price")
            @Expose
            private String price;

            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("product_type")
            @Expose
            private String product_type;
            @SerializedName("best_seller")
            @Expose
            private String best_seller;

            @SerializedName("top_rated")
            @Expose
            private String top_rated;
            @SerializedName("recommended")
            @Expose
            private String recommended;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("wishlist_status")
            @Expose
            private String wishlist_status;
            @SerializedName("createdAt")
            @Expose
            private String createdAt;
            @SerializedName("order_id")
            @Expose
            private int order_id;
            @SerializedName("product_id")
            @Expose
            private int product_id;
            @SerializedName("qty")
            @Expose
            private int qty;

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

            public String getSc_id() {
                return sc_id;
            }

            public void setSc_id(String sc_id) {
                this.sc_id = sc_id;
            }

            public String getProduct_title() {
                return product_title;
            }

            public void setProduct_title(String product_title) {
                this.product_title = product_title;
            }

            public String getProduct_image() {
                return product_image;
            }

            public void setProduct_image(String product_image) {
                this.product_image = product_image;
            }

            public String getShort_description() {
                return short_description;
            }

            public void setShort_description(String short_description) {
                this.short_description = short_description;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getWeight_type() {
                return weight_type;
            }

            public void setWeight_type(String weight_type) {
                this.weight_type = weight_type;
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

            public String getOffer() {
                return offer;
            }

            public void setOffer(String offer) {
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

            public String getProduct_type() {
                return product_type;
            }

            public void setProduct_type(String product_type) {
                this.product_type = product_type;
            }

            public String getBest_seller() {
                return best_seller;
            }

            public void setBest_seller(String best_seller) {
                this.best_seller = best_seller;
            }

            public String getTop_rated() {
                return top_rated;
            }

            public void setTop_rated(String top_rated) {
                this.top_rated = top_rated;
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

            public String getWishlist_status() {
                return wishlist_status;
            }

            public void setWishlist_status(String wishlist_status) {
                this.wishlist_status = wishlist_status;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getQty() {
                return qty;
            }

            public void setQty(int qty) {
                this.qty = qty;
            }
        }
    }
}