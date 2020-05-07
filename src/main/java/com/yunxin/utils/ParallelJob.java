package com.yunxin.utils;


import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Dingwei
 */
public class ParallelJob {

    private ExecutorService service;
    private int parallelSubTaskCount=1;
    private List<Integer> subTaskFinished;
    private TaskDoneListener taskDoneListener = null;
    private boolean isDone = false;
    private boolean isStarted = false;
    private Queue<Runnable> taskQueue = null;
    private String extState = "";
    private Map<String,Object> attribtes = new HashMap<>();

    public ParallelJob(int maxParallelSubTaskCount, TaskDoneListener taskDoneListener){
        this.parallelSubTaskCount = maxParallelSubTaskCount;
        service = Executors.newFixedThreadPool(this.parallelSubTaskCount);
        subTaskFinished = Collections.synchronizedList(new ArrayList<>());
        this.taskDoneListener = taskDoneListener;
        taskQueue = new LinkedList<>();
    }

    public synchronized void submitSubTask(Runnable runnable){
        try{
            subTaskFinished.add(-1);
            final int index = subTaskFinished.size() - 1;
            taskQueue.add(new Runnable() {
                @Override
                public void run() {

                    try {
                        runnable.run();
                    }catch (Throwable t){
                    }finally {
                        subTaskFinished.set(index,0);
                        if(allSubTaskFinishedCheck()){
                            taskDone();
                        }

                    }
                }
            });
        }catch (Throwable t){
        }
    }

    public void start(){
        if(!isStarted){
            if(taskQueue.size()==0){
                taskDone();
            }else{
                while(taskQueue.size()!=0){
                    service.execute(taskQueue.poll());
                }
            }

            //System.out.println("parallel job done: "+isDone);
        }

    }

    private void taskDone() {
        if(taskDoneListener!=null){
            try {
                taskDoneListener.onTaskDone(this);
                isDone = true;
                //System.out.println("parallel job done afterall: "+isDone);
            }catch (Throwable t){
                t.printStackTrace();
            }finally {
                service.shutdown();
            }
        }
    }


    public boolean isDone() {
        return isDone;
    }

    private synchronized boolean allSubTaskFinishedCheck(){
        int sum = 0;
        for(Integer tag: subTaskFinished){
            sum+=tag;
        }
        if(sum==0){
            return true;
        }else{
            return false;
        }
    }

    public String getExtState() {
        return extState;
    }

    public void setExtState(String extState) {
        this.extState = extState;
    }

    public Set<String> getAttributeNames(){
        return attribtes.keySet();
    }

    public Object getAttribute(String name){
        return attribtes.get(name);
    }

    public void setAttribte(String name, Object obj){
        attribtes.put(name,obj);
    }

    public static interface TaskDoneListener{
        public void onTaskDone(ParallelJob job);
    }

    public static void main(String[] args) throws ParseException {


    }
}
