package com.zerone.singleton;

/**
 * 饿汉式，直接方法上加class锁，保证线程安全，没有使用double-check，因为会涉及到happen-before规则以及java内存模型可见性问题，会导致线程安全
 * @author yxl
 * @since 2018/11/29
 */
public class LazyImpl {
    private LazyImpl(){}
    private static LazyImpl instance;
    public static synchronized LazyImpl getInstance(){
        if(instance==null){
            instance = new LazyImpl();
        }
        return instance;
    }
}
