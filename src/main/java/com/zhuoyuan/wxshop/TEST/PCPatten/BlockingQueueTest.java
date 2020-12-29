//package com.zhuoyuan.wxshop.TEST.PCPatten;
//
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.BlockingQueue;
//
///**
// * @ClassName classname
// * @Author MR.WANG
// * @Data 2020/12/18 2:49
// * @Version 1.0.0
// **/
//public class BlockingQueueTest {
//
//    public static void main(String[] args) {
//        BlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);
//
//
//        Runnable producer = () -> {
//            while (true) {
//                queue.put(new Object());
//            }
//        };
//
//        new Thread(producer).start();
//        new Thread(producer).start();
//
//        Runnable consumer = () -> {
//            while (true) {
//                queue.take();
//            }
//        };
//        new Thread(consumer).start();
//        new Thread(consumer).start();
//    }
//}
