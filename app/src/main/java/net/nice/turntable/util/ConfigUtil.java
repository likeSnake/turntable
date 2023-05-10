package net.nice.turntable.util;

import static net.nice.turntable.constant.MyAppApiConfig.config;

import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;

import net.nice.turntable.bean.ConfigBean;

import java.util.ArrayList;

public class ConfigUtil {
    public static void updateConfig(ArrayList<ConfigBean> result){
        String s = new Gson().toJson(result);
        MMKV.defaultMMKV().encode(config,s);
    }
}
