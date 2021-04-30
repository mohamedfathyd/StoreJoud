package com.khalej.storejoud.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class contact_copon {
    @SerializedName("payload")
    copon payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public copon getPayload() {
        return payload;
    }

    public void setPayload(copon payload) {
        this.payload = payload;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public class copon{
        @SerializedName("id")
        String id;
        @SerializedName("code")
        String code;
        @SerializedName("discount")
        double discount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }
    }
}
