package com.khalej.storejoud.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class contact_myOrders {
    @SerializedName("payload")
    List<orders> payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public List<orders> getPayload() {
        return payload;
    }

    public void setPayload(List<orders> payload) {
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
    public class orders{
        @SerializedName("id")
        String id;
        @SerializedName("total")
        double total;
        @SerializedName("day")
        String day;
        @SerializedName("payment_method")
        String payment_method;
        @SerializedName("total_before_promo_code")
        String total_before_promo_code;
        @SerializedName("order_status")
        String order_status;
        @SerializedName("promo_code")
        String promo_code;
        public String getId() {
            return id;
        }

        @SerializedName("invoice_id")
        String invoice_id;
        @SerializedName("products")
        List<products>products;
        public String getInvoice_id() {
            return invoice_id;
        }

        public void setInvoice_id(String invoice_id) {
            this.invoice_id = invoice_id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public String getTotal_before_promo_code() {
            return total_before_promo_code;
        }

        public void setTotal_before_promo_code(String total_before_promo_code) {
            this.total_before_promo_code = total_before_promo_code;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getPromo_code() {
            return promo_code;
        }

        public void setPromo_code(String promo_code) {
            this.promo_code = promo_code;
        }

        public List<contact_myOrders.products> getProducts() {
            return products;
        }

        public void setProducts(List<contact_myOrders.products> products) {
            this.products = products;
        }
    }

    public class products{
        @SerializedName("final_price")
        double final_price;
        @SerializedName("quantity")
        int quantity;
        @SerializedName("product")
        product product;

        public double getFinal_price() {
            return final_price;
        }

        public void setFinal_price(double final_price) {
            this.final_price = final_price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public contact_myOrders.product getProduct() {
            return product;
        }

        public void setProduct(contact_myOrders.product product) {
            this.product = product;
        }
    }

    public class product{
        @SerializedName("en_name")
        String en_name;
        @SerializedName("ar_name")
        String ar_name;
        @SerializedName("media_links")
        List<String>media_links;

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

        public List<String> getMedia_links() {
            return media_links;
        }

        public void setMedia_links(List<String> media_links) {
            this.media_links = media_links;
        }
    }
}
