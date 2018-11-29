package com.zerone.singleton;

/**
 * 饿汉式,jvm(Classloader)保证线程安全
 * @author yxl
 * @since 2018/11/29
 */
public class EarlyImpl {
    private static EarlyImpl instance = new EarlyImpl();
    private EarlyImpl(){}
    public static EarlyImpl getInstance(){
        return instance;
    }
}
