package cc;

import com.yunxin.utils.MethodCallSpeedControllerBuilder;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    static long startTime= 0;
    public static void main(String[] args) {
//        ParallelJob job = new ParallelJob(200, new ParallelJob.TaskDoneListener() {
//            @Override
//            public void onTaskDone(ParallelJob job) {
//                System.out.println("all task done:"+System.currentTimeMillis() +" dt="+(System.currentTimeMillis()-startTime));
//            }
//        });
//
//        for(int i=0;i<1000;i++){
//            job.submitSubTask(new ChatRoomTask(i,5));
//        }
//
//        startTime = System.currentTimeMillis();
//        job.start();

        ExecutorService es = Executors.newFixedThreadPool(50);
        for(int i=0;i<1000;i++){
            es.execute(new ChatRoomTask(i,5));
        }

    }

    public static class ChatRoomTask implements Runnable{

        List<ITask> tasks = new ArrayList<>();

        private int id;

        public ChatRoomTask(int id, int taskSize) {
            this.id = id;
            for(int i=0;i<taskSize;i++){
//                tasks.add(  new SendTask()  );
                tasks.add(MethodCallSpeedControllerBuilder.build(new SendTask(),ITask.class,50));
            }
        }

        @SneakyThrows
        @Override
        public void run() {
            for(ITask sendTask: tasks){
                sendTask.doTask();
                Thread.sleep(100);
            }
        }
    }

    public static class SendTask implements ITask{
        @SneakyThrows
        public void doTask(){
            Thread.sleep(200);
        }
    }


    public static interface ITask{
        public void doTask();
    }

}
