package com.songlin.dispatch.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.snappydb.SnappydbException;

import com.songlin.dispatch.model.account.AccessToken;
import com.songlin.dispatch.model.account.AccessTokenData;

public class AccountUtils {
    private static AccessTokenData accessTokenData = null;

    private static final String ACCOUNTKEY = "ACCOUNTKEY";

    public static AccessTokenData getAccessToken(Context context) {
        if (accessTokenData == null) {
            try {
                accessTokenData =
                        new Gson().fromJson(DBUtils.get(context, ACCOUNTKEY), AccessTokenData.class);
            } catch (SnappydbException e) {
                e.printStackTrace();
                accessTokenData = null;
            }
        }
        return accessTokenData;
    }

    public static void setAccessToken(Context context, AccessTokenData accessTokenData) {
        if (context == null || accessTokenData == null) return;

        AccountUtils.accessTokenData = null;
        try {
            DBUtils.put(context, ACCOUNTKEY, new Gson().toJson(accessTokenData));
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public static void setAccessToken(Context context, AccessToken accessToken) {
        if (context == null || accessToken == null) return;

        setAccessToken(context, accessToken.getData());
    }

    public static void clear(Context context) {

        AccessTokenData accessTokenData = new AccessTokenData();

        setAccessToken(context, accessTokenData);

    }
}
