package com.yunxin.utils;

import java.util.ArrayList;
import java.util.List;

public class Test implements ITest {

    static long startTime = 0;
    public static void main(String[] args) throws Throwable {



        ParallelJob job = new ParallelJob(500, new ParallelJob.TaskDoneListener() {
            @Override
            public void onTaskDone(ParallelJob job) {
                System.out.println("all taskDone in: "+ (System.currentTimeMillis() - startTime));
            }
        });


        for(int i=0;i<1000;i++){
            job.submitSubTask(new ChatRoomTask(i,2));
        }
        startTime = System.currentTimeMillis();
        job.start();




    }

    public static class ChatRoomTask implements Runnable
    {
        private int chatRoomId = 0;
        private int aFinalTaskSize = 10;
        private List<ITask> taskList = new ArrayList<>();

        public ChatRoomTask(int chatRoomId, int aFinalTaskSize) {
            this.chatRoomId = chatRoomId;
            this.aFinalTaskSize = aFinalTaskSize;
            for(int i=0;i<aFinalTaskSize;i++){
//                taskList.add(new AFinalTask());

                taskList.add(MethodCallSpeedControllerBuilder.build(new SendingTask(),ITask.class,10));
            }

        }

        @Override
        public void run() {
            for(int i=0;i<taskList.size();i++){
                taskList.get(i).doTask();
            }
        }
    }

    public static class SendingTask implements ITask{

        public void doTask(){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static interface ITask{
        public void doTask();
    }




}
