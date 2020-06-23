package com.yunxin.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

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

    public static Map<String,Object> getPcInfo(){
        try {
            Object systemInfoObject = Class.forName("oshi.SystemInfo").newInstance();
            Object hardwareObject = callObjectMethodWithReturnValue(systemInfoObject,"getHardware");
            Object computerSystemObject = callObjectMethodWithReturnValue(hardwareObject,"getComputerSystem");
            Object os = callObjectMethodWithReturnValue(systemInfoObject,"getOperatingSystem");
            Map<String,Object> map = new HashMap<>();
            map.put("os",os);
            map.put("cs",computerSystemObject);
            return map;

        } catch (Throwable e) {
            return null;
        }
    }

    public static String generateUUIDPerComputer(){
        try {
            Object systemInfoObject = Class.forName("oshi.SystemInfo").newInstance();
            Object hardwareObject = callObjectMethodWithReturnValue(systemInfoObject,"getHardware");
            Object computerSystemObject = callObjectMethodWithReturnValue(hardwareObject,"getComputerSystem");
            Object _cs_manufacture = callObjectMethodWithReturnValue(computerSystemObject,"getManufacturer");
            Object _cs_model = callObjectMethodWithReturnValue(computerSystemObject,"getModel");
            Object _cs_sn = callObjectMethodWithReturnValue(computerSystemObject,"getSerialNumber");
            Object firmware = callObjectMethodWithReturnValue(computerSystemObject,"getFirmware");
            Object _fm_manufacture = callObjectMethodWithReturnValue(firmware,"getManufacturer");
            Object _fm_name = callObjectMethodWithReturnValue(firmware,"getName");
            Object _fm_description = callObjectMethodWithReturnValue(firmware,"getDescription");
            Object _fm_releaseDate = callObjectMethodWithReturnValue(firmware,"getReleaseDate");
            Object _fm_version = callObjectMethodWithReturnValue(firmware,"getVersion");
            Object baseboard = callObjectMethodWithReturnValue(computerSystemObject,"getBaseboard");
            Object _bb_mf = callObjectMethodWithReturnValue(baseboard,"getManufacturer");
            Object _bb_model = callObjectMethodWithReturnValue(baseboard,"getModel");
            Object _bb_sn = callObjectMethodWithReturnValue(baseboard,"getSerialNumber");
            Object _bb_ver = callObjectMethodWithReturnValue(baseboard,"getVersion");
            Object os = callObjectMethodWithReturnValue(systemInfoObject,"getOperatingSystem");
            Object _os_family = callObjectMethodWithReturnValue(os,"getFamily");
            Object _os_manufacturer = callObjectMethodWithReturnValue(os,"getManufacturer");
            Object _os_bitness = callObjectMethodWithReturnValue(os,"getBitness");

            StringBuilder sb = new StringBuilder();
            sb.append(_cs_manufacture);
            sb.append(_cs_model);
            sb.append(_cs_sn);
            sb.append(_fm_manufacture);
            sb.append(_fm_name);
            sb.append(_fm_description);
            sb.append(_fm_releaseDate);
            sb.append(_fm_version);
            sb.append(_bb_mf);
            sb.append(_bb_model);
            sb.append(_bb_sn);
            sb.append(_bb_ver);
            sb.append(_os_family);
            sb.append(_os_manufacturer);
            sb.append(_os_bitness);

            return Work.Bytes.hex(Work.md5(sb.toString().getBytes())).replaceAll(" ","").toUpperCase();

        } catch (Throwable e) {
            return null;
        }

    }

    public static Object string2JSonObject(String s){
        return callStaticMethod("com.alibaba.fastjson.JSON","parseObject",s);
    }
    public  static <T> T json2JavaObject(Object jsonObject, Class<T> type){
        return (T) Reflection.callObjectMethodWithReturnValue(jsonObject,"toJavaObject",type);
    }

    public static <T> T jsonString2JavaObject(String str, Class<T> type){
        return json2JavaObject(string2JSonObject(str),type);
    }

    public static String javaObject2JSONString(Object object){
        return (String) callStaticMethod("com.alibaba.fastjson.JSON","toJSONString",new Class[]{Object.class},object);
    }


    public static void main(String[] args) {
        Reflection.generateUUIDPerComputer();
    }
}
