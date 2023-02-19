package com.library.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * Description: 获取Gson单例类
 * Others:
 */
public class GsonManager {

    /**
     * GsonManager实例
     */
    private static Gson instance;

    public static Gson getInstance() {
        if (instance == null) {
            synchronized (GsonManager.class) {
                if (instance == null) {
                    GsonBuilder builder = new GsonBuilder();
                    // Register an adapter to manage the date types as long values
                    builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                        @Override
                        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            return new Date(json.getAsJsonPrimitive().getAsLong());
                        }
                    });
                    instance = builder.create();
                }
            }
        }
        return instance;
    }

    //将Json数据解析成相应的映射对象
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        T result = instance.fromJson(jsonData, type);
        return result;
    }

    // 将Json数组解析成相应的映射对象列表
    public static <T> List<T> parseJsonListWithGson(String jsonData, Class<T> type) {
        List<T> result = instance.fromJson(jsonData, new TypeToken<List<T>>() {
        }.getType());
        return result;
    }
}
