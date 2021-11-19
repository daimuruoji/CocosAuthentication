package com.liyan.cocos_standard.https;

import com.liyan.base.web.response.LYBaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

//实名认证接口
public class LYRealNameAuthorizeResponse extends LYBaseResponse<String> {

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
