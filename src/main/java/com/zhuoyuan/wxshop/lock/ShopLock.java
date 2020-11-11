package com.zhuoyuan.wxshop.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 2 * @Author: MR.WangJie
 * 3 * @Date: 2020/11/11 16:17
 * 4
 */
public class ShopLock {

    private static  final  Lock lock = new ReentrantLock(false);

    public static Lock addLock(){
        return lock;
    }

}
