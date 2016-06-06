package com.songlin.chengyu.model.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Luo Songlin on 5/27/16.
 * Feature:
 */
public class ChatDetail {
    @SerializedName("id")
    @Expose
    public Object id;
    @SerializedName("author")
    @Expose
    public String author;
    @SerializedName("authorId")
    @Expose
    public Object authorId;
    @SerializedName("authorAvatar")
    @Expose
    public String avatar;
    @SerializedName("createdAt")
    @Expose
    public long createdAt;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("picture")
    @Expose
    public String picture;
    @SerializedName("createdStr")
    @Expose
    public String createdStr;

    //后期考虑评论
    @SerializedName("conversationId")
    @Expose
    public Object conversationId;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Object getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Object authorId) {
        this.authorId = authorId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedStr() {
        return createdStr;
    }

    public void setCreatedStr(String createdStr) {
        this.createdStr = createdStr;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Object getConversationId() {
        return conversationId;
    }

    public void setConversationId(Object conversationId) {
        this.conversationId = conversationId;
    }
}
