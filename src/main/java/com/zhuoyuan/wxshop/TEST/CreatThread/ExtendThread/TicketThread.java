package com.zhuoyuan.wxshop.TEST.CreatThread.ExtendThread;

/**
 * @ClassName classname
 * @Author MR.WANG
 * @Data 2020/12/17 16:20
 * @Version 1.0.0
 **/
public class TicketThread extends  Thread {
    private static  int ticketNum = 1000;

    @Override
    public void run(){
        while ( ticketNum > 0){
            System.out.println(Thread.currentThread().getName()+","+ticketNum --);
        }
    }
}
