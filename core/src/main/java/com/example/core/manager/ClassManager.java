package com.example.core.manager;


import com.example.core.utils.ReflectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 16-8-4.
 * 反射中间件，提高性能
 */
public class ClassManager {

    static class RefelctionCache {
        static Map<String, ClassInfo> classInfoMap = new HashMap<>();

        public static Class getClassInfo(String clazzName){
            ClassInfo classInfo = classInfoMap.get(clazzName);

            if(classInfo == null){
                //没有缓存Class文件，加载
                Class<?> clazz = ReflectUtils.getClazz(clazzName);
                //加载成功，缓存
                if(clazz != null){
                    classInfoMap.put(clazz.getName(),new ClassInfo(clazz));
                }
                return clazz;
            }else {
                //存在缓存Class文件，直接取出
                return classInfo.getClazz();
            }
        }
    }

    public static class ClassInfo{
        Class clazz;
        public ClassInfo(){}

        public ClassInfo(Class clazz){
            this.clazz = clazz;
        }

        Class getClazz(){
            return clazz;
        }

        void setClazz(Class clazz) {
            this.clazz = clazz;
        }
    }

    public static Class getClazz(String clazzName){
        return RefelctionCache.getClassInfo(clazzName);
    }
}
