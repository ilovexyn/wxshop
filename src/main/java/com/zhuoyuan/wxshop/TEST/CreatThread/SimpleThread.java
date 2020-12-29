package com.zhuoyuan.wxshop.TEST.CreatThread;

/**
 * @ClassName classname
 * @Author MR.WANG
 * @Data 2020/12/17 16:02
 * @Version 1.0.0
 **/
public class SimpleThread {

    private static  int ticketNum = 1000;

    public void run(){
        while ( ticketNum > 0){
            System.out.println(Thread.currentThread().getName()+","+ticketNum --);
        }
    }
}
