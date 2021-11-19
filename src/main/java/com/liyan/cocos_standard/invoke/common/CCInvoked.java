package com.liyan.cocos_standard.invoke.common;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import org.json.JSONException;

/**
 * @author syd
 * @fileName Hello
 * @date 2021/5/27 10:36
 * @description
 */
@Keep
public class CCInvoked {
    public static String TAG = "CCInvoked";
    public static boolean isRunning = false; //当manager为null时确保消息重发

    @Keep
    public static Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull final Message msg) {
            try {
                CCInvokingFactory.getInvoke(msg.arg1, msg.obj.toString()).invoke();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Keep
    public static void invoke(int type, String json) {
        Log.d(TAG, "invoke: type "  + type);
        Message obtain = Message.obtain();
        obtain.arg1 = type;
        obtain.obj = json;
        mHandler.sendMessage(obtain);
    }

}
