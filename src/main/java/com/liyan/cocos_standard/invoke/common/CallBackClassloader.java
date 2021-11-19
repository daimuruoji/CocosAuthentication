package com.liyan.cocos_standard.invoke.common;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CallBackClassloader {

    protected String type;
    protected Object json;
    private String evalString = "";

    public CallBackClassloader() {
    }

    public <JSON> CallBackClassloader(String type, JSON json) {
        this.type = type;
        this.json = json;
    }

    /**
     * Cocos2dxHelper.runOnGLThread(new Runnable() {
     *
     * @Override public void run() {
     * Cocos2dxJavascriptJavaBridge.evalString("cc.mig.UILayer.GetDataToShow(" + s + ")");
     * }
     * });
     */
    public void invokedCocosBridge() throws Exception {
        evalString = "cc.mig.SdkMgr .GetDataToAnti(" + type + "," + new Gson().toJson(json) + ")";

//        evalString = "cc.mig.SdkMgr .GetDataToAnti(23,{\"avatar\":\"\",\"coin\":0,\"coinRate\":10000,\"create_time\":\"2021-11-18 10:39:35\",\"extra_withdrawals\":0,\"extra_withdrawals_used\":false,\"force_active\":0,\"game_info\":\"\",\"hide_main_view\":true,\"isLogin\":true,\"isLogout\":false,\"isWithdrawals\":false,\"money\":\"0\"," +
//                "\"no_adult\":0,\"openid\":\"\"," +
//                "\"real_name_authorize_status\":0,\"shareUrl\":\"https://liyan-mobi-video.oss-cn-beijing.aliyuncs.com/follow-video/app/jbxxl.apk\",\"signDay\":0,\"signStatus\":0,\"status\":0,\"todayCoin\":0,\"token\":\"MWQ4NDA2NzNlMDU2YmYwMDAxZTFiMzZmMzQ3M2M2MGEyNXdldnRIbg\\u003d\\u003d\",\"unionid\":\"\",\"user_code\":\"44722013\",\"user_cpl_status\":false,\"user_id\":\"1d840673e056bf0001e1b36f3473c60a\",\"user_invite_hold\":0,\"user_invite_reward\":0,\"user_invite_success\":0,\"user_invite_total\":0,\"user_invited\":false,\"user_level\":1,\"user_level_score\":0,\"user_location_allowed\":false,\"user_medal\":\"青铜\",\"user_money_count\":0,\"user_name\":\"游客_44722013\",\"user_next_level_score\":3000,\"video_coin\":0,\"withdrawals_count\":0,\"withdrawals_limit\":2})";

        Class<?> cocos2dxJavascriptJavaBridge = Class.forName("org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge");
        Method evalStringMethod = cocos2dxJavascriptJavaBridge.getDeclaredMethod("evalString", String.class);

        Class<?> cocos2dxHelper = Class.forName("org.cocos2dx.lib.Cocos2dxHelper");
        Method runOnGLThread = cocos2dxHelper.getDeclaredMethod("runOnGLThread", Runnable.class);
        runOnGLThread.invoke(null, new Runnable() {
            @Override
            public void run() {
                try {
                    evalStringMethod.invoke(null, evalString);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * SDKWrapper.getInstance().getContext();
     */
    public Context getContext() throws Exception {
        Class<?> sdkWrapper = Class.forName("org.cocos2dx.javascript.SDKWrapper");
        Method getInstance = sdkWrapper.getDeclaredMethod("getInstance");
        Object invoke = getInstance.invoke(new Object());
        Method getContext = sdkWrapper.getDeclaredMethod("getContext");
        getContext.setAccessible(true);//打破封装
        return (Context) getContext.invoke(invoke);
    }

    public static class Builder<JSON> extends CallBackClassloader {

        public Builder() {
        }

        public Builder(String type, JSON json) {
            super(type, json);
        }

        public Builder<JSON> setType(String type) {
            this.type = type;
            return this;
        }

        public Builder<JSON> setJson(JSON json) {
            this.json = json;
            return this;
        }
    }
}
