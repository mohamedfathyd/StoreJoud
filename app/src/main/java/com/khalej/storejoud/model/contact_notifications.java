package com.khalej.storejoud.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class contact_notifications {
    @SerializedName("payload")
    List<notification> payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public List<notification> getPayload() {
        return payload;
    }

    public void setPayload(List<notification> payload) {
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
    public class notification{
        @SerializedName("id")
        String id;
        @SerializedName("created_at")
        String created_at;
        @SerializedName("data")
        data data;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public contact_notifications.data getData() {
            return data;
        }

        public void setData(contact_notifications.data data) {
            this.data = data;
        }
    }
    public class data{
        @SerializedName("message")
        String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
