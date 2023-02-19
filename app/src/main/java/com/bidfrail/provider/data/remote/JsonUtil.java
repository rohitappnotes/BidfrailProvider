package com.bidfrail.provider.data.remote;

import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


import java.util.Collection;
import java.util.List;

public class JsonUtil {
    /***
     * 得到错误码
     * @param response
     * @return
     */
    public static int getErrorCode(String response) {
        int errorCode = -1;
        try {
            JsonObject jsonObj = new JsonParser().parse(response).getAsJsonObject();
            errorCode = jsonObj.get("errorCode").getAsInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorCode;
    }

    public static String getErrorMsg(String response) {
        String errorMsg = null;
        try {
            JsonObject jsonObj = new JsonParser().parse(response).getAsJsonObject();
            errorMsg = jsonObj.get("errorMsg").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorMsg;
    }
}
