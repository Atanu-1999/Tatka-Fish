package com.example.licious.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddAddressResponse {
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
        @SerializedName("address_id")
        @Expose
        private Integer address_id;
        @SerializedName("user_id")
        @Expose
        private String user_id;
        @SerializedName("address_line_one")
        @Expose
        private String address_line_one;
        @SerializedName("address_line_two")
        @Expose
        private String address_line_two;
        @SerializedName("landmark")
        @Expose
        private String landmark;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("mobile_number")
        @Expose
        private String mobile_number;
        @SerializedName("address_type")
        @Expose
        private String address_type;
        @SerializedName("address_status")
        @Expose
        private String address_status;

        public Integer getAddress_id() {
            return address_id;
        }

        public void setAddress_id(Integer address_id) {
            this.address_id = address_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAddress_line_one() {
            return address_line_one;
        }

        public void setAddress_line_one(String address_line_one) {
            this.address_line_one = address_line_one;
        }

        public String getAddress_line_two() {
            return address_line_two;
        }

        public void setAddress_line_two(String address_line_two) {
            this.address_line_two = address_line_two;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getMobile_number() {
            return mobile_number;
        }

        public void setMobile_number(String mobile_number) {
            this.mobile_number = mobile_number;
        }

        public String getAddress_type() {
            return address_type;
        }

        public void setAddress_type(String address_type) {
            this.address_type = address_type;
        }

        public String getAddress_status() {
            return address_status;
        }

        public void setAddress_status(String address_status) {
            this.address_status = address_status;
        }
    }
}
