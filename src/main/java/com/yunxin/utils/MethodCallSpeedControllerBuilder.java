package com.yunxin.utils;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class MethodCallSpeedControllerBuilder<T> {


    private Object originObject = null;

    private Class<T> methodInterface = null;

    public MethodCallSpeedControllerBuilder(Object originObject, Class<T> methodInterface) {
        this.originObject = originObject;
        this.methodInterface = methodInterface;
    }

    public T build(int speed){
        if(SpeedControllerHandler.configurationInstance.containsKey(methodInterface)){
            SpeedControllerHandler.SpeedControllerConfiguration config = SpeedControllerHandler.configurationInstance.get(methodInterface);
            config.speed = speed;
        }else{
            SpeedControllerHandler.SpeedControllerConfiguration config = new SpeedControllerHandler.SpeedControllerConfiguration(speed);
            SpeedControllerHandler.configurationInstance.put(methodInterface,config);
        }
        return (T) Proxy.newProxyInstance(
                originObject.getClass().getClassLoader(),
                new Class[]{methodInterface},
                new SpeedControllerHandler(originObject, SpeedControllerHandler.configurationInstance.get(methodInterface))
        );
    }


    public static <T> T build(Object o, Class<T> tInterface, int speed){
        if(SpeedControllerHandler.configurationInstance.containsKey(tInterface)){
            SpeedControllerHandler.SpeedControllerConfiguration config = SpeedControllerHandler.configurationInstance.get(tInterface);
            config.speed = speed;
        }else{
            SpeedControllerHandler.SpeedControllerConfiguration config = new SpeedControllerHandler.SpeedControllerConfiguration(speed);
            SpeedControllerHandler.configurationInstance.put(tInterface,config);
        }

        return (T) Proxy.newProxyInstance(
                o.getClass().getClassLoader(),
                new Class[]{tInterface},
                new SpeedControllerHandler(o, SpeedControllerHandler.configurationInstance.get(tInterface))
        );


    }




    public static class SpeedControllerHandler implements InvocationHandler {

        public static Map<Class, SpeedControllerHandler.SpeedControllerConfiguration> configurationInstance = new HashMap<>();


        SpeedControllerHandler.SpeedControllerConfiguration controllerConfiguration;

        private Object object;


        public SpeedControllerHandler(Object object, SpeedControllerHandler.SpeedControllerConfiguration controllerConfiguration) {
            this.object = object;
            this.controllerConfiguration = controllerConfiguration;
        }



        @SneakyThrows
        public  long ajust(){
            long current = System.currentTimeMillis()/1000;
            synchronized (controllerConfiguration.map){
                if(controllerConfiguration.map.containsKey(current)){
                    AtomicLong l = controllerConfiguration.map.get(current);
                    long size = l.addAndGet(1);
                    if(size>controllerConfiguration.speed){
                        Thread.sleep(1000);
                        return ajust();
                    }

                }else{
                    controllerConfiguration.map.put(current,new AtomicLong(0));
                    return ajust();

                }
            }


            return current;
        }



        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            synchronized (controllerConfiguration.counter){
                long id= controllerConfiguration.counter.addAndGet(1);
                long current = ajust();
                if(id%100==0){
                    System.out.println("["+id+"]+c["+current+"]->"+controllerConfiguration.map.get(current)+ " total: "+controllerConfiguration.counter.get() );
                }
            }

            Object o = null;
            o = method.invoke(this.object,args);

            return o;
        }

        public static class SpeedControllerConfiguration{

            public SpeedControllerConfiguration(float speed) {
                this.speed = speed;
            }

            private  float speed = 1000;

            private  AtomicLong counter = new AtomicLong(0);

            private  Map<Long,AtomicLong> map = new HashMap<>();

            public float getSpeed() {
                return speed;
            }

            public void setSpeed(float speed) {
                this.speed = speed;
            }

            public AtomicLong getCounter() {
                return counter;
            }

            public void setCounter(AtomicLong counter) {
                this.counter = counter;
            }

            public Map<Long, AtomicLong> getMap() {
                return map;
            }

            public void setMap(Map<Long, AtomicLong> map) {
                this.map = map;
            }
        }
    }
}
