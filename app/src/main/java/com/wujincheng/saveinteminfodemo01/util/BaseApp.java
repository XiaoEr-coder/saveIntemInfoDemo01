package com.wujincheng.saveinteminfodemo01.util;

/* Author:
 * Time:2023/9/11 20:51
 * E-mail: 2370449363@qq.com
 */

import android.util.Log;

import com.tencent.mmkv.MMKV;
import com.wujincheng.saveinteminfodemo01.MainActivity;


public class BaseApp {
    private static final String TAG = "BaseApp";
    private static BaseApp mBaseApp = null;

    public static BaseApp getInstance() {
        if (mBaseApp == null) {
            synchronized (BaseApp.class) {
                if (mBaseApp == null) {
                    mBaseApp = new BaseApp();
                }
            }
        }
        return mBaseApp;
    }

    private MMKV kv = null;


    public void init() {
        String rootDir = MMKV.initialize(MainActivity.getContext());
        kv = MMKV.defaultMMKV();
    }

    public void setDate_Int(String key, int val) {
        Log.d(TAG, "setDate_Int: " + key);
        kv.encode(key, val);
    }

    public int getDate_Int(String key) {
        Log.d(TAG, "setDate_Int: " + key);
        return kv.decodeInt(key);
    }

    public void setDate_String(String key, String val) {
        Log.d(TAG, "setDate_Int: " + val);
        kv.encode(key, val);
    }

    public String getDate_String(String key) {
        Log.d(TAG, "setDate_Int: " + key);
        return kv.decodeString(key);
    }

}
