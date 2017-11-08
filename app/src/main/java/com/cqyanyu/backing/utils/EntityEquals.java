package com.cqyanyu.backing.utils;

import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityEquals {

    public static List<Map<String, String>> allEquals(Object oldObj, Object obj2) {
        List<Map<String, String>> mList = new ArrayList<>();
        Method[] methods = oldObj.getClass().getMethods();
        for (Method method : methods) {
            try {
                Object oldData = method.invoke(oldObj);
                Object newData = method.invoke(obj2);
                if (!objEquals(oldData, newData)) {
                    if (oldData instanceof Date) {
                        //处理日期类型
                        Date oldD = (Date) oldData;
                        Date newD = (Date) newData;
                        oldData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(oldD);
                        newData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(newD);
                    }
                    if (!TextUtils.equals((String) oldData, (String) newData)) {
                        //处理String结果
                        Map<String, String> map = new HashMap<>();
                        map.put("method", method.getName());
                        map.put("oldValue", (String) oldData);
                        map.put("newValue", (String) newData);
                        mList.add(map);
                    }
                }
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return mList;
    }

    private static boolean objEquals(Object cs1, Object cs2) {
        if (cs1 == cs2) {
            return true;
        } else if (cs1 != null && cs2 != null) {
            return cs1.equals(cs2);
        } else {
            return false;
        }
    }
}
