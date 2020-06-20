package com.yunxin.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Factory {

    public static <T> T build(Class<T> cls){
        try {
            final Object templateObject = cls.newInstance();
            T protocolTranslator = (T) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Object o = method.invoke(templateObject,args);
                    return o;
                }
            });
            return protocolTranslator;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ProxyInvocationHandler getGenericProxyInvocationHandler(Object object){
        return new ProxyInvocationHandler(object);
    }

    public static <T> T buildSafe(Class<T> cls){
        Object object = Reflection.callObjectMethodWithReturnValue(cls,"newInstance");
        T protocolTranslator = (T) Reflection.callStaticMethod("java.lang.reflect.Proxy","newProxyInstance", new Class[]{ClassLoader.class, Class[].class, InvocationHandler.class}, cls.getClassLoader(),cls.getInterfaces(), getGenericProxyInvocationHandler(object));

        return protocolTranslator;
    }

    public static void main(String[] args) {
        ITest test = Factory.buildSafe(Test.class);
        System.out.println(test.getClass());

    }

}
