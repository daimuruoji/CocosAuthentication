package com.liyan.cocos_standard.https;

import static com.liyan.cocos_standard.Constants.URL_LOGIN;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.liyan.base.utils.LYDeviceUtils;
import com.liyan.base.utils.LYLog;
import com.liyan.base.utils.LYMd5;
import com.liyan.base.web.request.LYBaseRequest;
import com.liyan.cocos_standard.CocosManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class LYLoginRequest extends LYBaseRequest<LYLoginResponse> {
    protected Context mContext;
    protected String captcha;
    protected String phone;

    protected String openid;
    protected String unionid;
    protected String nickname;
    protected String avatar;

    public LYLoginRequest(Context mContext) {
        TAG = "LoginRequest";
        this.mContext = mContext;
    }

    public LYLoginRequest(Context mContext, LYLoginRequest request) {
        TAG = "LoginRequest";
        this.mContext = mContext;
        this.captcha = request.captcha;
        this.phone = request.phone;

        this.openid = request.openid;
        this.unionid = request.unionid;
        this.nickname = request.nickname;
        this.avatar = request.avatar;
    }

    @Override
    protected String getUrl() {
        return URL_LOGIN;
    }

    @Override
    protected Class<LYLoginResponse> getResponseType() {
        return LYLoginResponse.class;
    }

    @Override
    protected int getMethod() {
        return LYBaseRequest.METHOD_POST;
    }

    @Override
    protected void fillParams(HashMap<String, String> params) {
        String imei = LYDeviceUtils.getImei(mContext);
        if (TextUtils.isEmpty(imei)) {
            imei = CocosManager.instance().getOAID();
        }
        params.put("device_imei", imei);
        params.put("device_imsi", TextUtils.isEmpty(LYDeviceUtils.getImsi(mContext)) ? "" : LYDeviceUtils.getImsi(mContext));
        params.put("device_oaid", CocosManager.instance().getOAID());
        params.put("device_id", LYDeviceUtils.getAndroidId(mContext));
        params.put("device_mac", LYDeviceUtils.getMac(mContext));
        params.put("device_model", Build.MODEL);
        params.put("device_brand", Build.BRAND);
        params.put("screen_size", LYDeviceUtils.getDeviceHeight(mContext) + "x" + LYDeviceUtils.getDeviceWidth(mContext));
        params.put("os_version", Build.VERSION.RELEASE);
        String user_agent = getUserAgent();
        if (!TextUtils.isEmpty(user_agent)) {
            params.put("user_agent", user_agent);
        }
        params.put("os_version_code", String.valueOf(Build.VERSION.SDK_INT));


        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
            params.put("captcha", captcha);
        }

        if (!TextUtils.isEmpty(openid)) {
            params.put("open_id", openid);
            params.put("union_id", unionid);
            params.put("user_name", nickname);
            params.put("avatar", avatar);
        }


        Set<String> keys = params.keySet();
        List<String> keyList = new ArrayList<>(keys);
        Collections.sort(keyList);
        String str = "";
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            LYLog.d(TAG, "key: " + key);
            String value = params.get(key);
            if (!TextUtils.isEmpty(value)) {
                if (str.length() == 0) {
                    str += (key + "=" + value);
                } else {
                    str += ("&" + key + "=" + value);
                }
            }

        }
        LYLog.v(TAG, "params: " + str);
        String sign = LYMd5.md5(str);
        params.put("sign", sign);

        params.put("channel", CocosManager.instance().getOAID());
        params.put("app_id", CocosManager.instance().getAppId());

        super.fillParams(params);
    }


    private String getUserAgent() {
        try {
            WebView webview = new WebView(mContext);
            webview.layout(0, 0, 0, 0);
            WebSettings settings = webview.getSettings();
            return settings.getUserAgentString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected String getSignKey() {
        return "";
    }

    public static class Builder extends LYLoginRequest {
        public Builder(Context mContext) {
            super(mContext);
            this.mContext = mContext;
        }

        public Builder setOpenid(String openid) {
            this.openid = openid;
            return this;
        }

        public Builder setUnionid(String unionid) {
            this.unionid = unionid;
            return this;
        }

        public Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder setAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setCaptcha(String captcha) {
            this.captcha = captcha;
            return this;
        }


        public LYLoginRequest build() {
            return new LYLoginRequest(mContext, this);
        }
    }
}
