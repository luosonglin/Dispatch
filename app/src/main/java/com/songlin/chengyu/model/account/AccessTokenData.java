package com.songlin.chengyu.model.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessTokenData {
    /*{
      "success": true,
      "data": {
        "code": 400,
        "error": "用户名或密码错误"
        "scope": "merchant",
        "tokenType": "bearer",
        "accessToken": "3157a4da699d8b00d1d68926aa2e07f2",
        "ownerId": 301,
        "createdAt": 1438572928,
        "expiresIn": 5184000,
        "refreshToken": "a9132e5a2639e23cf09ebadbdce266bb"
      }
    }*/
    @Expose
    @SerializedName("code")
    private int Code;
    @Expose
    @SerializedName("error")
    private String error;
    @Expose
    @SerializedName("scope")
    private String scope;
    @Expose
    @SerializedName("tokenType")
    private String tokenType;
    @Expose
    @SerializedName("accessToken")
    private String accessToken;
    @Expose
    @SerializedName("ownerId")
    private Integer ownerId;
    @Expose
    @SerializedName("createAt")
    private Integer createdAt;
    @Expose
    @SerializedName("expiresIn")
    private Integer expiresIn;
    @Expose
    @SerializedName("refreshToken")
    private String refreshToken;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    /**
     * @return The scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope The scope
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    public AccessTokenData withScope(String scope) {
        this.scope = scope;
        return this;
    }

    /**
     * @return The tokenType
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * @param tokenType The tokenType
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public AccessTokenData withTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    /**
     * @return The accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken The accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public AccessTokenData withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * @return The ownerId
     */
    public Integer getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId The ownerId
     */
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public AccessTokenData withOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    /**
     * @return The createdAt
     */
    public Integer getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The createdAt
     */
    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public AccessTokenData withCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * @return The expiresIn
     */
    public Integer getExpiresIn() {
        return expiresIn;
    }

    /**
     * @param expiresIn The expiresIn
     */
    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public AccessTokenData withExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    /**
     * @return The refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param refreshToken The refreshToken
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public AccessTokenData withRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

}
