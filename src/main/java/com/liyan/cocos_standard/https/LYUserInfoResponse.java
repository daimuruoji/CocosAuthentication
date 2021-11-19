package com.liyan.cocos_standard.https;

import com.liyan.base.utils.LYDateUtils;
import com.liyan.base.utils.LYJson;
import com.liyan.base.web.response.LYBaseResponse;
import com.liyan.cocos_standard.CocosManager;
import com.liyan.cocos_standard.bean.LYUserInfo;

import org.json.JSONObject;

//获取用户信息
public class LYUserInfoResponse extends LYBaseResponse<LYUserInfo> {
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
                data = LYJson.parseObject(jsonObject.getString("user_info"), LYUserInfo.class);
            }
            if (jsonObject.has("time_stamp")) {
                CocosManager.instance().webTime = LYDateUtils.parseDateToLong(jsonObject.optString("time_stamp"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
