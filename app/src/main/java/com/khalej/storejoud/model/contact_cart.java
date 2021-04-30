package com.khalej.storejoud.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class contact_cart {
    @SerializedName("payload")
    List<cart> payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public List<cart> getPayload() {
        return payload;
    }

    public void setPayload(List<cart> payload) {
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
    public class cart{
        @SerializedName("id")
        String id;
        @SerializedName("quantity")
        int quantity;
        @SerializedName("total_price")
        double total_price;
        @SerializedName("product")
        product product;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(double total_price) {
            this.total_price = total_price;
        }

        public contact_cart.product getProduct() {
            return product;
        }

        public void setProduct(contact_cart.product product) {
            this.product = product;
        }
    }

    public class product{
        @SerializedName("id")
        String id;
        @SerializedName("price")
        String price;
        @SerializedName("name_by_lang")
        String name_by_lang;
        @SerializedName("media_links")
        List<String> media_links;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getName_by_lang() {
            return name_by_lang;
        }

        public void setName_by_lang(String name_by_lang) {
            this.name_by_lang = name_by_lang;
        }

        public List<String> getMedia_links() {
            return media_links;
        }

        public void setMedia_links(List<String> media_links) {
            this.media_links = media_links;
        }
    }
}
