package com.yunxin.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Reflection {

    /**
     * 反射调用方法
     * @param object
     * @param method
     * @param paras
     */
    public static void callObjectMethod(Object object, String method, Object... paras){
        try{
            Class[] pclss = new Class[paras.length];
            for(int i=0;i<paras.length;i++){
                if(paras[i] instanceof Boolean){
                    pclss[i] = Boolean.TYPE;
                }else if(paras[i] instanceof Integer){
                    pclss[i] = Integer.TYPE;
                }else if(paras[i] instanceof Long){
                    pclss[i] = Long.TYPE;
                }
                else{
                    pclss[i] = paras[i].getClass();
                }

            }
            Class mo = object.getClass();
            try {
                Method me = mo.getMethod(method,pclss);
                me.setAccessible(true);
                me.invoke(object,paras);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }catch (Throwable t){}
    }

    public static Object callObjectMethodWithReturnValue(Object object, String method, Object... paras){
        try{
            Class[] pclss = new Class[paras.length];
            for(int i=0;i<paras.length;i++){
                if(paras[i] instanceof Boolean){
                    pclss[i] = Boolean.TYPE;
                }else if(paras[i] instanceof Integer){
                    pclss[i] = Integer.TYPE;
                }else if(paras[i] instanceof Long){
                    pclss[i] = Long.TYPE;
                }
                else{
                    pclss[i] = paras[i].getClass();
                }

            }
            Class mo = object.getClass();
            try {
                Method me = mo.getMethod(method,pclss);
                me.setAccessible(true);
                return me.invoke(object,paras);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }catch (Throwable t){}
        return null;
    }

    public static Object callStaticMethod(String className,String methodName,Object... paras){
        try {
            Class cl = Class.forName(className);
            Class[] classes = new Class[paras.length];
            for(int i=0;i<paras.length;i++){
                classes[i] = paras[i].getClass();
            }
            Method method = cl.getDeclaredMethod(methodName, classes);
            return method.invoke(cl,paras);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static Object callStaticMethod(String className,String methodName, Class[] classes, Object... paras){
        try {

            Class cl = (Class) Reflection.callStaticMethod("java.lang.Class","forName",className);
            Method method = cl.getDeclaredMethod(methodName, classes);
            return method.invoke(cl,paras);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void set(Object objectT, String field,Object value) throws Throwable {
        Class cls = null;
        Object object = null;
        if(objectT instanceof Class){
            cls = (Class) objectT;
        }else{
            object = objectT;
            cls = object.getClass();
        }

        Field f = cls.getDeclaredField(field);
        f.setAccessible(true);
        if(Modifier.isStatic(f.getModifiers())){
            f.set(cls,value);
        }else{
            f.set(object,value);
        }

    }

    public static Object get(Object objectT,String field) throws Throwable{

        Class cls = null;
        Object object = null;
        if(objectT instanceof Class){
            cls = (Class) objectT;
        }else{
            object = objectT;
            cls = object.getClass();
        }
        Field f = cls.getDeclaredField(field);
        f.setAccessible(true);
        if(Modifier.isStatic(f.getModifiers())){
            return f.get(cls);
        }else{
            return f.get(object);
        }

    }

    public static void set(Class objectT, String field,Object value) throws Throwable {
        Class cls = null;
        Object object = null;
        if(objectT instanceof Class){
            cls = (Class) objectT;
        }else{
            object = objectT;
            cls = object.getClass();
        }

        Field f = cls.getDeclaredField(field);
        f.setAccessible(true);
        if(Modifier.isStatic(f.getModifiers())){
            f.set(cls,value);
        }else{
            f.set(object,value);
        }

    }

    public static Object get(Class objectT,String field) throws Throwable{

        Class cls = null;
        Object object = null;
        if(objectT instanceof Class){
            cls = (Class) objectT;
        }else{
            object = objectT;
            cls = object.getClass();
        }
        Field f = cls.getDeclaredField(field);
        f.setAccessible(true);
        if(Modifier.isStatic(f.getModifiers())){
            return f.get(cls);
        }else{
            return f.get(object);
        }

    }



}
