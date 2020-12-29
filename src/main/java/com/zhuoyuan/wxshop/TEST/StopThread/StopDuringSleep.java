package com.zhuoyuan.wxshop.TEST.StopThread;

/**
 * @ClassName classname
 * @Author MR.WANG
 * @Data 2020/12/17 21:16
 * @Version 1.0.0
 **/
public class StopDuringSleep {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () ->{
          int num = 0;
          try{
              while (!Thread.currentThread().isInterrupted() && num <1000){
                  System.out.println(num);
                  num++;
                  Thread.sleep(1000000);
              }
          }catch (InterruptedException e){
              Thread.currentThread().interrupt();
              e.printStackTrace();
          }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(5);
        thread.interrupt();
    }
}
