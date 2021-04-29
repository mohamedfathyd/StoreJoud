package com.khalej.storejoud.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class contact_products {
    @SerializedName("payload")
    List<product> payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public List<product> getPayload() {
        return payload;
    }

    public void setPayload(List<product> payload) {
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
    public class product{
       @SerializedName("name_by_lang")
        String name_by_lang;
       @SerializedName("en_name")
       String en_name;
       @SerializedName("ar_name")
       String ar_name;
       @SerializedName("price")
       String price;
       @SerializedName("en_overview")
       String en_overview;
       @SerializedName("ar_overview")
       String ar_overview;
       @SerializedName("has_special_price")
       String has_special_price;
       @SerializedName("average_rate")
       String average_rate;
       @SerializedName("media_links")
       List<String>media_links;
       @SerializedName("id")
        String id;
       @SerializedName("price_after_discount")
       String price_after_discount;

        public String getPrice_after_discount() {
            return price_after_discount;
        }

        public void setPrice_after_discount(String price_after_discount) {
            this.price_after_discount = price_after_discount;
        }

        public String getName_by_lang() {
            return name_by_lang;
        }

        public void setName_by_lang(String name_by_lang) {
            this.name_by_lang = name_by_lang;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEn_name() {
            return en_name;
        }

        public void setEn_name(String en_name) {
            this.en_name = en_name;
        }

        public String getAr_name() {
            return ar_name;
        }

        public void setAr_name(String ar_name) {
            this.ar_name = ar_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getEn_overview() {
            return en_overview;
        }

        public void setEn_overview(String en_overview) {
            this.en_overview = en_overview;
        }

        public String getAr_overview() {
            return ar_overview;
        }

        public void setAr_overview(String ar_overview) {
            this.ar_overview = ar_overview;
        }

        public String getHas_special_price() {
            return has_special_price;
        }

        public void setHas_special_price(String has_special_price) {
            this.has_special_price = has_special_price;
        }

        public String getAverage_rate() {
            return average_rate;
        }

        public void setAverage_rate(String average_rate) {
            this.average_rate = average_rate;
        }

        public List<String> getMedia_links() {
            return media_links;
        }

        public void setMedia_links(List<String> media_links) {
            this.media_links = media_links;
        }
    }
}
