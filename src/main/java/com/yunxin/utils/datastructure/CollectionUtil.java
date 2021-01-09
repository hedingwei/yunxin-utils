package com.yunxin.utils.datastructure;

import com.yunxin.utils.Factory;

import java.util.*;

public class CollectionUtil {
    public static <T> void union(Collection<T> collection, Iterable<T>... iterables){
        for(Iterable<T> it: iterables){
            for(T o: it){
                collection.add(o);
            }
        }
    }

    public static <T> void intersect(Collection<T> collection, Iterable<T> it1, Iterable<T> it2, Comparator<T> comparator){
        for(T o1: it1){
            for(T o2: it2){
                if(comparator!=null){
                    if(comparator.compare(o1,o2)==0){
                        collection.add(o1);
                    }
                }else{
                    if(o1.equals(o2)){
                        collection.add(o1);
                    }
                }
            }
        }
    }

    public static <T> void intersect(Collection<T> collection,Comparator<T> comparator, Iterable<T>... iterables){
        if(iterables.length==0){
            return;
        }else if(iterables.length==1){
            for(Iterable<T> it: iterables){
                for(T t:it){
                    collection.add(t);
                }
            }
        }else if(iterables.length==2){
           Iterable<T> it1 = iterables[0];
           Iterable<T> it2 = iterables[1];
           intersect(collection,it1,it2,comparator);
           return;
        }else{
            Iterator<Iterable<T>> iterator = Arrays.stream(iterables).iterator();
            Iterable<T> it1 = iterables[0];
            Iterable<T> it2 = iterables[1];
            Collection<T> inCollection = Factory.build(collection.getClass());
            intersect(inCollection,it1,it2,comparator);
            if(inCollection.isEmpty()){
                return;
            }else{

                for(int i=2;i<iterables.length;i++){

                    Collection<T> newCollection = Factory.build(collection.getClass());
                    intersect(newCollection,inCollection,iterables[i],comparator);
                    inCollection = newCollection;
                    if(inCollection.isEmpty()){
                        return;
                    }
                }
                collection.addAll(inCollection);
            }
        }
    }

    public static <T> void subs(Collection<T> collection, Iterable<T>... iterables){
        for(Iterable<T> iterable:iterables){
            for(T obj: iterable){
                try{
                    collection.remove(obj);
                }catch (Throwable t){}
            }
        }
    }



    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(1);
        list.add("a");
        list.add(2);
        list.add(new byte[0]);

        List list1 = new ArrayList();
        list1.add(2);
        list1.add(1);
        list1.add("a");

        List list2 = new ArrayList();
        list2.add("a");
        list2.add("3");
        list2.add(2);
        list2.add(1);

        Collection set = new ArrayList();
        CollectionUtil.intersect(set,null,list,list1,list2);
        System.out.println(set);

        CollectionUtil.subs(list1,list);
        System.out.println(list1);

    }
}
