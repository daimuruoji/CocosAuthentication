package com.liyan.cocos_standard.invoke;

import static com.liyan.cocos_standard.CocosManager.getToken;

import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liyan.base.web.request.LYBaseRequest;
import com.liyan.base.web.response.LYBaseResponse;
import com.liyan.cocos_standard.CocosManager;
import com.liyan.cocos_standard.bean.LYRealName;
import com.liyan.cocos_standard.https.LYRealNameAuthorizeRequest;
import com.liyan.cocos_standard.invoke.common.CCCommonInvoke;
import com.liyan.cocos_standard.invoke.common.CCInvoked;
import com.liyan.cocos_standard.invoke.common.CallBackClassloader;
import com.liyan.cocos_standard.invoke.common.ICCInvoke;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CCRealNameAuthorize extends CCCommonInvoke implements ICCInvoke {
    public CCRealNameAuthorize(int type, String json) {
        super(type, json);
    }

    @Override
    public void invoke() throws JSONException {
        anuthorize();
    }

    /**
     * 实名认证
     */
    private void anuthorize() {
        LYRealName realName = new Gson().fromJson(invokeJson, LYRealName.class);
        new LYRealNameAuthorizeRequest.Builder(mContext)
                .setToken(getToken(mContext))
                .setCardId(realName.CardID)
                .setRealName(realName.realName)
//                .setCardId("14222319951016091X")
//                .setRealName("孙煜鼎")
                .build()
                .request(new LYBaseRequest.RequestListener() {
                    @Override
                    public void onPretreatment(LYBaseResponse lyBaseResponse) {

                    }

                    @Override
                    public void onResponse(LYBaseResponse lyBaseResponse) {
                        if (lyBaseResponse.isSuccess()) {
                            try {
//                                new CallBackClassloader.Builder<String>()
//                                        .setType(String.valueOf(invokeType))
//                                        .setJson("绑定成功")
//                                        .invokedCocosBridge();
                                //认证通过 重新获取
                                Message obtain = Message.obtain();
                                obtain.arg1 = 23;
                                obtain.obj = "";
                                CCInvoked.mHandler.sendMessageDelayed(obtain, 1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(CocosManager.instance().getContext(), lyBaseResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CocosManager.instance().getContext(), lyBaseResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(CocosManager.instance().getContext(), "网络异常", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
