package com.liyan.cocos_standard;

import android.content.Context;

import com.liyan.base.utils.LYConfigUtils;
import com.liyan.cocos_standard.bean.LYUserInfo;
import com.liyan.cocos_standard.invoke.common.CallBackClassloader;

public class CocosManager {
    private String TAG = getClass().getSimpleName();

    private String channel = "market";
    private String app_id = "30001";
    private final String OAID = "OAID";
    private final String token = "user_token";
    private static CocosManager manager;
    public long webTime;
    private boolean isSupportOaid;
    private LYUserInfo mUserInfo;

    public CocosManager() {
    }

    public static CocosManager instance() {
        if (manager == null)
            manager = new CocosManager();
        return manager;
    }

    public Context getContext() {
        try {
            return new CallBackClassloader.Builder().getContext();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getToken(Context mContext) {
        return LYConfigUtils.getString(mContext, manager.token);
    }

    public static void setToken(Context mContext, String token) {
        LYConfigUtils.setString(mContext, manager.token, token);
    }

    public void setSupportOaid(boolean supportOaid) {
        isSupportOaid = supportOaid;
    }

    public void setOAID(String oaid) {
        LYConfigUtils.setString(getContext(), OAID, oaid);
    }

    public String getOAID() {
        return LYConfigUtils.getString(getContext(), OAID);
    }

    public String getChannel() {
        return channel;
    }

    public String getAppId() {
        return app_id;
    }

    public LYUserInfo getUserInfo() {
        return mUserInfo == null ? new LYUserInfo() : mUserInfo;
    }

    public void setUserInfo(LYUserInfo mUserInfo) {
        this.mUserInfo = mUserInfo;
    }
}
