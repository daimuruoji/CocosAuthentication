package com.liyan.cocos_standard.invoke;


import static com.liyan.cocos_standard.CocosManager.getToken;
import static com.liyan.cocos_standard.CocosManager.setToken;

import android.content.Context;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;

import com.liyan.base.web.request.LYBaseRequest;
import com.liyan.base.web.response.LYBaseResponse;
import com.liyan.cocos_standard.CocosManager;
import com.liyan.cocos_standard.bean.LYUserInfo;
import com.liyan.cocos_standard.https.LYLoginRequest;
import com.liyan.cocos_standard.https.LYUserInfoRequest;
import com.liyan.cocos_standard.https.LYUserInfoResponse;
import com.liyan.cocos_standard.invoke.common.CCCommonInvoke;
import com.liyan.cocos_standard.invoke.common.CallBackClassloader;
import com.liyan.cocos_standard.invoke.common.ICCInvoke;

import org.json.JSONException;

/**
 * 登陆
 */
public class CCLogin extends CCCommonInvoke implements ICCInvoke {

    public CCLogin(int type) {
        super(type);
    }

    @Override
    public void invoke() throws JSONException {
        login();
//        LYUserInfo userInfo = CocosManager.instance().getUserInfo();
//        if (TextUtils.isEmpty(userInfo.user_id)) {
//            //没有用户信息重新登陆
//            login();
//        } else {
//            //返回当前userinfo
//            callBack(userInfo);
//        }
    }

    public void login() {
        if (!TextUtils.isEmpty(getToken(mContext))) {
            getUserInfo(mContext, getToken(mContext));
        } else {
            register();
        }
    }

    /**
     * 注册
     */
    private void register() {
        new LYLoginRequest.Builder(mContext).build().request(new LYBaseRequest.RequestListener() {
            @Override
            public void onPretreatment(LYBaseResponse lyBaseResponse) {
                Log.d(TAG, "onPretreatment: ");
            }

            @Override
            public void onResponse(LYBaseResponse response) {
                if (response.isSuccess()) {
                    String token = (String) response.getData();
                    Log.d(TAG, "onResponse: successful");
                    setToken(mContext, token);
                    getUserInfo(mContext, token);
                } else {
                    Log.d(TAG, "onResponse: fail");
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "onError: ", e);
            }
        });
    }

    private void getUserInfo(Context mContext, String token) {
        new LYUserInfoRequest.Builder(mContext)
                .setToken(token)
                .build()
                .request(new LYBaseRequest.RequestListener() {
                    @Override
                    public void onPretreatment(LYBaseResponse lyBaseResponse) {

                    }

                    @Override
                    public void onResponse(LYBaseResponse response) {
                        if (!response.isSuccess()) {
                            Log.d(TAG, "onResponse: fail");
                        } else {
                            Log.d(TAG, "onResponse: successful");
                            LYUserInfoResponse userInfoResponse = (LYUserInfoResponse) response;
                            LYUserInfo userInfo = userInfoResponse.getData();
                            userInfo.token = getToken(mContext);
                            if (TextUtils.isEmpty(userInfo.user_name)) {
                                userInfo.user_name = "游客_" + userInfo.user_code;
                            }
                            userInfo.isLogin = true;
                            userInfo.isLogout = false;
                            userInfo.token = token;
                            CocosManager.instance().setUserInfo(userInfo);
                            setToken(mContext, userInfo.token);
                            callBack(userInfo);
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d(TAG, "onError: ");
                    }
                });
    }

    /**
     * result告知cocos
     */
    private void callBack(LYUserInfo userInfo) {
        try {
            new CallBackClassloader.Builder<LYUserInfo>()
                    .setType(String.valueOf(invokeType))
                    .setJson(userInfo)
                    .invokedCocosBridge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
