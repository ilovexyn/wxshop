package com.zhuoyuan.wxshop.TEST.CreatThread.ThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName classname
 * @Author MR.WANG
 * @Data 2020/12/17 16:53
 * @Version 1.0.0
 **/
public class Pool {
    /**
     * corepoolsize 最小线程数，一般设置为服务器核心数量
     * maxpoolsiz 最大线程数，当队列满后会扩容到最大线程数
     * keepalivetime 当线程池中线程数大于corepoolsize 并且无新增任务时，销毁等待的最大时间。
     * unit 销毁时间单位
     * workQueue 工作队列
     * handler 饱和策略。当现在同时进行的任务数量大于最大线程数量并且队列也已经放满后，线程池采取的一些策略
     * AbortPolicy直接抛出异常，RejectedExecutionException.
     * CallerRunsPolicy ,调用执行自己的线程来完成任务，当您的任务不能抛弃时采取这个策略，这个策略会给系统带来额外的开销，影响性能。
     * DiscardPolicy 直接抛弃掉
     * DiscardOldestPolicy 丢弃掉最老的任务。
     */
    private static final int CORE_POOL_SIZE = 5; //核心线程数为 5
    private static final int MAX_POOL_SIZE = 10; //最大线程数 10
    private static final int QUEUE_CAPACITY = 100; //
    private static final Long KEEP_ALIVE_TIME = 1L; //当线程数大于核心线程数时，多余的空闲线程存活的最长时间
    private static final  TimeUnit unit = TimeUnit.SECONDS;
    RejectedExecutionHandler handler =  new ThreadPoolExecutor.CallerRunsPolicy();
    public static void main(String[] args) {
        try{
            //有界队列
            ArrayBlockingQueue<Runnable> queuea = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
            //无界队列
            // LinkedBlockingQueue<Runnable> queuea = new LinkedBlockingQueue<>();
            //等待队列
            // SynchronousQueue<Runnable> queuea = new SynchronousQueue<>();

            RejectedExecutionHandler handler =  new ThreadPoolExecutor.AbortPolicy();
            ThreadPoolExecutor threadpool =
                    new ThreadPoolExecutor(
                            CORE_POOL_SIZE,
                            MAX_POOL_SIZE,
                            KEEP_ALIVE_TIME,
                            unit,
                            queuea,
                            handler
                    );
            for (int i = 0; i < 5; i++) {
                threadpool.execute(new Thread(new Ticket(),"Thread".concat(i+"")));
                System.out.println("线程池中当前线程数："+threadpool.getPoolSize()+",核心线程数："+threadpool.getCorePoolSize());
                System.out.println("队列大小："+queuea.size());
                if(queuea.size() > 0){
                    System.out.println("阻塞队列有线程了,队列中阻塞线程数："+queuea.size()+",线程池中执行任务线程数："+threadpool.getActiveCount()+"");
                }
            }
            threadpool.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
