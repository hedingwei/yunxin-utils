package com.yunxin.utils.BinSturcture.core;

import com.yunxin.utils.Work;
import com.yunxin.utils.bytes.Pack;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YxBinUtil {
    public static byte[] toBytes(Object object) throws IllegalAccessException {
        Pack pack = new Pack();
        Class cls = object.getClass();

        String typeName = cls.getTypeName();
        if("int".equals(typeName)||"java.lang.Integer".equals(typeName)){
            pack.setInt((Integer) object);
            return pack.getAll();
        }else if("double".equals(typeName)||"java.lang.Double".equals(typeName)){
            pack.setDouble((Double) object);
            return pack.getAll();
        }else if("float".equals(typeName)||"java.lang.Float".equals(typeName)){
            pack.setFloat((Float) object);
            return pack.getAll();
        }else if("short".equals(typeName)||"java.lang.Short".equals(typeName)){
            pack.setShort((Short) object);
            return pack.getAll();
        }else if("boolean".equals(typeName)||"java.lang.Boolean".equals(typeName)){
            pack.setByte((byte) (((boolean)object==true)?2:1));
        }else if(object instanceof String){
            pack.setStr((String) object);
            return pack.getAll();
        } else if(object instanceof List){
            List list = (List) object;
            pack.setInt(list.size());
            Class iCls = null;
            for(int i=0;i<list.size();i++){
                pack.setBin(toBytes(list.get(i)));
            }
        }else if(object instanceof Map){
            Map map = (Map) object;
            int size = map.size();
            pack.setInt(size);
            for(Object key:map.keySet()){
                if(!(key instanceof String)){
                    return null;
                }else{
                    if(map.get(key).getClass().getDeclaredAnnotation(YxBin.class)==null){
                        return null;
                    }else{
                        pack.setStr((String) key);
                        pack.setBin(toBytes(map.get(key)));
                        return pack.getAll();
                    }
                }
            }
        }else{
            Annotation annotation = cls.getDeclaredAnnotation(YxBin.class);
            if(annotation==null){
                return null;
            }else{
                Field[] fields = cls.getDeclaredFields();
                for(Field field: fields){
                    if(field.getDeclaredAnnotation(YxBin.class)!=null){
                        field.setAccessible(true);
                        Object value = field.get(object);
                        if(value==null){
                            System.out.println(field.getName()+" null value");
                            return null;
                        }
                        String fieldTypeName = field.getType().getTypeName();
                        if("int".equals(fieldTypeName)||"java.lang.Integer".equals(fieldTypeName)){
                            pack.setInt((Integer) value);
                        }else if("double".equals(fieldTypeName)||"java.lang.Double".equals(fieldTypeName)){
                            pack.setDouble((Double) value);
                        }else if("float".equals(fieldTypeName)||"java.lang.Float".equals(fieldTypeName)){
                            pack.setFloat((Float) value);
                        }else if("short".equals(fieldTypeName)||"java.lang.Short".equals(fieldTypeName)){
                            pack.setShort((Short) value);
                        }else if("boolean".equals(fieldTypeName)||"java.lang.Boolean".equals(fieldTypeName)){
                            pack.setByte((byte) (((boolean)value==true)?2:1));
                        }else if(value instanceof String){
                            pack.setStr((String) value);
                        } else if(value instanceof List){
                            List list = (List) value;
                            pack.setInt(list.size());
                            Class iCls = null;
                            for(int i=0;i<list.size();i++){
                                pack.setBin(toBytes(list.get(i)));
                            }
                        }else if(value instanceof Map){
                            Map map = (Map) value;
                            int size = map.size();
                            pack.setInt(size);
                            for(Object key:map.keySet()){
                                if(!(key instanceof String)){
                                    return null;
                                }else{
                                    if(map.get(key).getClass().getDeclaredAnnotation(YxBin.class)==null){
                                        return null;
                                    }else{
                                        pack.setStr((String) key);
                                        pack.setBin(toBytes(map.get(key)));
                                    }
                                }
                            }
                        }
                    }
                }
                return pack.getAll();
            }
        }

        return null;
    }

    public static void main(String[] args) throws IllegalAccessException {

        Cmd cmd = new Cmd();
        cmd.setAngle(3.14);
        cmd.setC(2.1f);
        cmd.setId(1);
        cmd.setMsg("hello");
        cmd.setType(22);
        cmd.setStrings(new ArrayList<>());
        cmd.setCmdMap(new HashMap<>());

        cmd.getStrings().add("cccdd");
        cmd.setCt(false);

        Cmd cmd1 = new Cmd();
        cmd1.setAngle(3.14);
        cmd1.setC(2.1f);
        cmd1.setId(1);
        cmd1.setMsg("hello");
        cmd1.setType(22);
        cmd1.setCt(true);
        cmd1.setStrings(new ArrayList<>());
        cmd1.setCmdMap(new HashMap<>());

        cmd.getCmdMap().put("abc",cmd1);



        System.out.println(Work.Bytes.hex(toBytes(cmd)));
    }


}
