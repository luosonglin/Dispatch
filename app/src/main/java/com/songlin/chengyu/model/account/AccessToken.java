package com.songlin.chengyu.model.account;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.songlin.chengyu.utils.GsonUtils;


/**
 * Created by nami on 15/2/8.
 */
public class AccessToken {

    @Expose
    @SerializedName("success")
    private Boolean success;
    @Expose
    @SerializedName("data")
    private AccessTokenData data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public AccessToken withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    /**
     * @return The data
     */
    public AccessTokenData getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(AccessTokenData data) {
        this.data = data;
    }

    public AccessToken withData(AccessTokenData data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        Gson gson = GsonUtils.createGson();
        return gson.toJson(this);
    }
}

