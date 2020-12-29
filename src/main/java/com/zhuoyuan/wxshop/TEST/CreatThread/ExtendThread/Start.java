package com.zhuoyuan.wxshop.TEST.CreatThread.ExtendThread;

/**
 * @ClassName classname
 * @Author MR.WANG
 * @Data 2020/12/17 16:21
 * @Version 1.0.0
 **/
public class Start {
    public static void main(String[] args) {
        TicketThread ticketThread =  new TicketThread();
        Thread thread1 = new Thread(ticketThread,"窗口一");
        Thread thread2 = new Thread(ticketThread,"窗口二");
        Thread thread3 = new Thread(ticketThread,"窗口三");
        thread1.start();
        thread2.start();
        thread3.start();
    }

}
