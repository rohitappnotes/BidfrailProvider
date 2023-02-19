package com.library.utilities;

import java.util.List;

/**
 * @author：Jason
 * @date：2020/7/22 18:45
 * @email：1129847330@qq.com
 * @description:
 */
public class CollectionUtils {
    public static boolean isEmpty(List list) {
        return list == null || list.size() <= 0;
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length <= 0;
    }

    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }
}