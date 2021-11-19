package com.liyan.cocos_standard.invoke.common;

import android.content.Context;

import com.liyan.cocos_standard.CocosManager;


/**
 * @author syd
 * @fileName CCCommon
 * @date 2021/5/28 10:46
 * @description
 */
public class CCCommonInvoke {
    protected String TAG = "CCInvokingFactory";
    protected Context mContext;
    protected int invokeType;  //type
    protected String invokeJson; //重要数据

    public CCCommonInvoke() {
        this(-1, "");
    }

    public CCCommonInvoke(int invokeType) {
        this(invokeType, "");
    }

    public CCCommonInvoke(int invokeType, String invokeJson) {
        mContext = CocosManager.instance().getContext();
        this.invokeType = invokeType;
        this.invokeJson = invokeJson;
    }

    public int getInvokeType() {
        return invokeType;
    }

    public String getInvokeJson() {
        return invokeJson;
    }
}

