package com.liyan.cocos_standard.invoke.common;


import androidx.annotation.Keep;

import com.liyan.cocos_standard.invoke.CCInit;
import com.liyan.cocos_standard.invoke.CCLogin;
import com.liyan.cocos_standard.invoke.CCRealNameAuthorize;


/**
 * @author syd
 * @fileName CCInvoking
 * @date 2021/5/27 16:28
 * @description
 */
public class CCInvokingFactory {
    @Keep
    public static ICCInvoke getInvoke(int type, String json) {
        switch (type) {
            case 22:
                return new CCInit();
            case 23:
                return new CCLogin(type);
            case 24:
                return new CCRealNameAuthorize(type, json);
            default:
                return null;
        }
    }
}
