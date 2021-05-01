package com.khalej.storejoud.model;
import com.google.gson.annotations.SerializedName;


public class contact_profile {
    @SerializedName("payload")
    profile payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public profile getPayload() {
        return payload;
    }

    public void setPayload(profile payload) {
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
    public class profile{
        @SerializedName("id")
        String id;
        @SerializedName("logo_url")
        String logo_url;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }
    }
}
