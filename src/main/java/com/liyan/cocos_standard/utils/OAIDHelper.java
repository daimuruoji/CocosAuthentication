package com.liyan.cocos_standard.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bun.miitmdid.core.ErrorCode;
import com.bun.miitmdid.core.MdidSdkHelper;
import com.bun.miitmdid.interfaces.IIdentifierListener;
import com.bun.miitmdid.interfaces.IdSupplier;
import com.liyan.base.utils.LYLog;
import com.liyan.cocos_standard.CocosManager;


/**
 * Time: 2021/3/31 15:36.
 * Author: 庄宏岩.
 * Description:
 */
public class OAIDHelper implements IIdentifierListener {

    private AppIdsUpdater _listener;

    public OAIDHelper(AppIdsUpdater callback) {
        _listener = callback;

    }

    public void getDeviceIds(Context cxt) {

        long timeb = System.currentTimeMillis();
        // 方法调用
        int nres = CallFromReflect(cxt);

        long timee = System.currentTimeMillis();
        long offset = timee - timeb;
        if (nres == ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT) {//不支持的设备

        } else if (nres == ErrorCode.INIT_ERROR_LOAD_CONFIGFILE) {//加载配置文件出错

        } else if (nres == ErrorCode.INIT_ERROR_MANUFACTURER_NOSUPPORT) {//不支持的设备厂商

        } else if (nres == ErrorCode.INIT_ERROR_RESULT_DELAY) {//获取接口是异步的，结果会在回调中返回，回调执行的回调可能在工作线程

        } else if (nres == ErrorCode.INIT_HELPER_CALL_ERROR) {//反射调用出错

        }
        LYLog.d("OAIDHelper", "return value: " + String.valueOf(nres));

    }

    /*
     * 方法调用
     *
     * */
    private int CallFromReflect(Context cxt) {
        return MdidSdkHelper.InitSdk(cxt, true, this);
    }

    /*
     * 获取相应id
     *
     * */
    @Override
    public void OnSupport(boolean isSupport, IdSupplier _supplier) {
        if (_supplier == null) {
            return;
        }
        String oaid = _supplier.getOAID();
        String vaid = _supplier.getVAID();
        String aaid = _supplier.getAAID();
        StringBuilder builder = new StringBuilder();
        builder.append("support: ").append(isSupport ? "true" : "false").append("\n");
        builder.append("OAID: ").append(oaid).append("\n");
        builder.append("VAID: ").append(vaid).append("\n");
        builder.append("AAID: ").append(aaid).append("\n");
        String idstext = builder.toString();
        LYLog.d("OAIDHelper", idstext);
        CocosManager.instance().setSupportOaid(isSupport);
        if (_listener != null) {
            _listener.OnIdsAvalid(oaid);
        }
    }

    public interface AppIdsUpdater {
        void OnIdsAvalid(@NonNull String ids);
    }

}