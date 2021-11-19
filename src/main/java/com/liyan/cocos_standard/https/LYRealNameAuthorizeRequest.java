package com.liyan.cocos_standard.https;


import static com.liyan.cocos_standard.Constants.URL_REAL_NAME_AUTHORIZE;

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

public class LYRealNameAuthorizeRequest extends LYBaseRequest<LYRealNameAuthorizeResponse> {
    protected Context mContext;
    protected String token;
    protected String real_name;
    protected String card_no;

    public LYRealNameAuthorizeRequest(Context mContext) {
        TAG = "LYRealNameAuthorizeRequest";
        this.mContext = mContext;
    }

    public LYRealNameAuthorizeRequest(Context mContext, LYRealNameAuthorizeRequest request) {
        TAG = "LYRealNameAuthorizeRequest";
        this.mContext = mContext;
        this.token = request.token;
        this.real_name = request.real_name;
        this.card_no = request.card_no;
    }

    @Override
    protected String getUrl() {
        return URL_REAL_NAME_AUTHORIZE;
    }

    @Override
    protected Class<LYRealNameAuthorizeResponse> getResponseType() {
        return LYRealNameAuthorizeResponse.class;
    }

    @Override
    protected int getMethod() {
        return LYBaseRequest.METHOD_POST;
    }

    @Override
    protected void fillParams(HashMap<String, String> params) {
        params.put("token", token);
        params.put("real_name", real_name);
        params.put("card_no", card_no);



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
        str += ("&key=" + getSignKey());
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

    public static class Builder extends LYRealNameAuthorizeRequest {
        public Builder(Context mContext) {
            super(mContext);
            this.mContext = mContext;
        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setCardId(String card_no) {
            this.card_no = card_no;
            return this;
        }

        public Builder setRealName(String real_name) {
            this.real_name = real_name;
            return this;
        }


        public LYRealNameAuthorizeRequest build() {
            return new LYRealNameAuthorizeRequest(mContext, this);
        }
    }
}
