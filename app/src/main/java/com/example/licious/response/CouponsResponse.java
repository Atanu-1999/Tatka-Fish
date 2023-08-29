package com.example.licious.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CouponsResponse {
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
        @SerializedName("coupon_name")
        @Expose
        private String couponName;
        @SerializedName("coupon_code")
        @Expose
        private String couponCode;
        @SerializedName("applyig_amount")
        @Expose
        private Integer applyigAmount;
        @SerializedName("off_amount")
        @Expose
        private Integer offAmount;
        @SerializedName("coupon_type")
        @Expose
        private String couponType;
        @SerializedName("coupon_for")
        @Expose
        private String couponFor;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("tc")
        @Expose
        private String tc;
        @SerializedName("startdate")
        @Expose
        private String startdate;
        @SerializedName("enddate")
        @Expose
        private String enddate;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("created")
        @Expose
        private String created;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public Integer getApplyigAmount() {
            return applyigAmount;
        }

        public void setApplyigAmount(Integer applyigAmount) {
            this.applyigAmount = applyigAmount;
        }

        public Integer getOffAmount() {
            return offAmount;
        }

        public void setOffAmount(Integer offAmount) {
            this.offAmount = offAmount;
        }

        public String getCouponType() {
            return couponType;
        }

        public void setCouponType(String couponType) {
            this.couponType = couponType;
        }

        public String getCouponFor() {
            return couponFor;
        }

        public void setCouponFor(String couponFor) {
            this.couponFor = couponFor;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTc() {
            return tc;
        }

        public void setTc(String tc) {
            this.tc = tc;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}
