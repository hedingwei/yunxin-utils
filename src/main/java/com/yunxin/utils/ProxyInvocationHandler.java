package com.yunxin.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyInvocationHandler implements InvocationHandler {

    private Object target;

    public ProxyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            try{
                // using reflect do sth... checking
            }catch (Throwable t){
                return null;
            }
            return method.invoke(target, args);
        }catch (Throwable t){
            return null;
        }
    }
}
