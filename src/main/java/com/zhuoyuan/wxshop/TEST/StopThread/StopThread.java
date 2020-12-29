package com.zhuoyuan.wxshop.TEST.StopThread;

/**
 * @ClassName classname
 * @Author MR.WANG
 * @Data 2020/12/17 21:02
 * @Version 1.0.0
 **/
public class StopThread implements Runnable {

    @Override
    public void run(){
        int count = 0;
        while(!Thread.currentThread().isInterrupted() && count <1000){
            System.out.println("conut =" + count++);
        }
    }

    public static void main(String[] args) throws  InterruptedException{
        Thread thread = new Thread(new StopThread());
        thread.start();
        Thread.sleep(10);
       thread.interrupt();
    }

}
