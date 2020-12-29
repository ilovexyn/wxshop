package com.zhuoyuan.wxshop.TEST.StopThread.VolatileCanNotStop;

import java.util.concurrent.BlockingQueue;

/**
 * @ClassName classname
 * @Author MR.WANG
 * @Data 2020/12/18 0:34
 * @Version 1.0.0
 **/
public class Consumer {
    BlockingQueue storage;
    public Consumer(BlockingQueue storage) {
        this.storage = storage;
    }
    public boolean needMoreNums() {
        if (Math.random() > 0.97) {
            return false;
        }
        return true;
    }

}
