package com.liyan.cocos_standard.https;


import com.liyan.base.web.response.LYBaseResponse;

import org.json.JSONObject;

//登录
public class LYLoginResponse extends LYBaseResponse<String> {
    @Override
    public void parseData(String response) {
        setResponse(response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            setResultCode(jsonObject.optInt("code"));
            if (jsonObject.has("msg")) {
                setMsg(jsonObject.optString("msg"));
            } else if (jsonObject.has("message")) {
                setMsg(jsonObject.optString("message"));
            }
            if (getResultCode() == STATUS_OK) {
                data = jsonObject.optString("token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
