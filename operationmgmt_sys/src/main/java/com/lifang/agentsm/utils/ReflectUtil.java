package com.lifang.agentsm.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class ReflectUtil {
    public static Object getFieldValue(Object obj, String fieldName) {
        Object result = null;
        Field field = ReflectUtil.getField(obj, fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                result = field.get(obj);
            }
            catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block   
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                // TODO Auto-generated catch block   
                e.printStackTrace();
            }
        }
        return result;
    }

    private static Field getField(Object obj, String fieldName) {
        Field field = null;
        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            }
            catch (NoSuchFieldException e) {
                //这里不用做处理，子类没有该字段可能对应的父类有，都没有就返 回null
            }
        }
        return field;

    }

    public static void setFieldValue(Object obj, String fieldName, Object fieldValue) {
        Field field = ReflectUtil.getField(obj, fieldName);
        if (field != null) {
            try {
                field.setAccessible(true);
                field.set(obj, fieldValue);
            }
            catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block   
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                // TODO Auto-generated catch block   
                e.printStackTrace();
            }
        }
    }
    
    
    /**
     * 获取传入Class对象的name字段，包含父类字段
     * @author 程康
     * <p>创建时间2014-5-15下午1:59:26</p>
     * @param clazz
     * @param name
     * @return
     */
    public static Field getDeclaredField(Class clazz,String name){
        Field field = null;
        try {
            if(clazz == Object.class){
                return null;
            }
            field = clazz.getDeclaredField(name);
        } catch (SecurityException e) {
        } catch (NoSuchFieldException e) {
            return getDeclaredField(clazz.getSuperclass(), name);
        }
        return field;
    }
    
    public static List<Field> getDeclaredFields(Class clazz){
        return getDeclaredFields(clazz,null);
    }
    
    private static List<Field> getDeclaredFields(Class clazz,List<Field> fields){
        if(fields == null)fields = new ArrayList<Field>();
        if(clazz != Object.class){
            fields.addAll(ArrayUtils.asList(clazz.getDeclaredFields()));
            return getDeclaredFields(clazz.getSuperclass(),fields);
        }
        return fields;
    }

}
