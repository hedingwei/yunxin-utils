package com.yunxin.utils.ui;

import java.awt.*;
import java.util.Vector;

public class Animator implements Runnable{

    private Vector<Image> images = new Vector<>();

    private long step=100;

    private boolean isStarted = false;

    private int index = 0;

    private int repeatTimes = 0;

    private long playDuraion = 0;

    private AnimationPlayListener playListener = null;

    private AnimationPlayListener listener = null;

    public void setImages(Image... images){
        for(Image i: images){
            this.images.add(i);
        }
    }

    public void setStep(long step) {
        this.step = step;
    }

    public void start(){
        if(!isStarted){
            isStarted = true;
            new Thread(this).start();
            if(playDuraion>0){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long tic = 0;
                        while (true){
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            tic= tic + 50;
                            if(tic>=playDuraion){
                                isStarted = false;
                                break;
                            }
                        }
                    }
                }).start();
            }
        }


    }

    public void stop(){
        if(isStarted){
            isStarted=false;
        }else{

        }
    }

    Runnable stopListener = null;
    public void stop(Runnable runnable ){
        this.stopListener = runnable;
        if(isStarted){
            isStarted=false;
        }else{
            runnable.run();
        }
    }



    @Override
    public void run() {
        try{
            if(playListener!=null){
                playListener.onStart();
            }
        }catch (Throwable t){}
        int rep = 0;
        while (isStarted){
            index++;
            index = index%images.size();
            try {
                Thread.sleep(step);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                updateFrame(index, images.get(index));
            }catch (Throwable t){}

            if(repeatTimes>0){
                if(index==0){
                    rep++;
                    if(rep>=repeatTimes){
                        break;
                    }
                }
            }


        }

        try{
            if(playListener!=null){
                playListener.onStop();
                if(stopListener!=null){
                    stopListener.run();
                }
            }
        }catch (Throwable t){}
        isStarted = false;
    }

    public void updateFrame(int index,Image image){
        if(playListener!=null){
            playListener.onPlay(index,image);
        }
    }

    public AnimationPlayListener getPlayListener() {
        return playListener;
    }

    public void setPlayListener(AnimationPlayListener playListener) {
        this.playListener = playListener;
    }

    public int getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(int repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public long getPlayDuraion() {
        return playDuraion;
    }

    public void setPlayDuraion(long playDuraion) {
        this.playDuraion = playDuraion;
    }


}
