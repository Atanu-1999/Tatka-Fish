package com.example.licious.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SlotResponse {
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
        @SerializedName("slot_name")
        @Expose
        private String slot_name;
        @SerializedName("delivery_charge")
        @Expose
        private Integer delivery_charge;
        @SerializedName("status")
        @Expose
        private String status;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSlot_name() {
            return slot_name;
        }

        public void setSlot_name(String slot_name) {
            this.slot_name = slot_name;
        }

        public Integer getDelivery_charge() {
            return delivery_charge;
        }

        public void setDelivery_charge(Integer delivery_charge) {
            this.delivery_charge = delivery_charge;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
