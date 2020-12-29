package com.zhuoyuan.wxshop.TEST.CreatThread.impRun;

/**
 * @ClassName classname
 * @Author MR.WANG
 * @Data 2020/12/17 16:05
 * @Version 1.0.0
 **/
public class TicketThread implements  Runnable {

    private static  int ticketNum = 1000;

    public void run(){
        while ( ticketNum > 0){
            System.out.println(Thread.currentThread().getName()+","+ticketNum --);
        }
    }

}
