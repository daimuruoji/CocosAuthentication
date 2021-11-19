package com.liyan.cocos_standard.invoke;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.liyan.cocos_standard.CocosManager;
import com.liyan.cocos_standard.invoke.common.CCCommonInvoke;
import com.liyan.cocos_standard.invoke.common.ICCInvoke;
import com.liyan.cocos_standard.utils.OAIDHelper;

import org.json.JSONException;

public class CCInit extends CCCommonInvoke implements ICCInvoke {
    @Override
    public void invoke() throws JSONException {
        init();
    }

    private void init() {
        Log.d("TAG========", "init: ");
        //获取oaid
        OAIDHelper oaidHelper = new OAIDHelper(new OAIDHelper.AppIdsUpdater() {
            @Override
            public void OnIdsAvalid(@NonNull String ids) {
                Log.d("BaseApplication", "OAID：" + ids);
                if (!TextUtils.isEmpty(ids)) {
                    CocosManager.instance().setOAID(ids);
                }
            }
        });
        oaidHelper.getDeviceIds(CocosManager.instance().getContext());

        Toast.makeText(CocosManager.instance().getContext(), "初始化成功", Toast.LENGTH_SHORT).show();
    }
}
