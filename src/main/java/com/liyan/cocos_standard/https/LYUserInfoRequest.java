package com.liyan.cocos_standard.https;


import static com.liyan.cocos_standard.Constants.URL_GET_USERINFO;

import android.content.Context;
import android.text.TextUtils;

import com.liyan.base.utils.LYLog;
import com.liyan.base.utils.LYMd5;
import com.liyan.base.web.request.LYBaseRequest;
import com.liyan.cocos_standard.CocosManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class LYUserInfoRequest extends LYBaseRequest<LYUserInfoResponse> {
    protected Context mContext;
    protected String token;

    public LYUserInfoRequest(Context mContext) {
        TAG = "UserInfoRequest";
        this.mContext = mContext;
    }

    public LYUserInfoRequest(Context mContext, LYUserInfoRequest request) {
        TAG = "UserInfoRequest";
        this.mContext = mContext;
        this.token = request.token;
    }

    @Override
    protected String getUrl() {
        return URL_GET_USERINFO;
    }

    @Override
    protected Class<LYUserInfoResponse> getResponseType() {
        return LYUserInfoResponse.class;
    }

    @Override
    protected int getMethod() {
        return LYBaseRequest.METHOD_POST;
    }

    @Override
    protected void fillParams(HashMap<String, String> params) {
        params.put("token", this.token);

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

        params.put("channel", CocosManager.instance().getChannel());
        params.put("app_id", CocosManager.instance().getAppId());
        super.fillParams(params);
    }

    @Override
    protected String getSignKey() {
        return "";
    }

    public static class Builder extends LYUserInfoRequest {
        public Builder(Context mContext) {
            super(mContext);
            this.mContext = mContext;
        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }


        public LYUserInfoRequest build() {
            return new LYUserInfoRequest(mContext, this);
        }
    }
}
