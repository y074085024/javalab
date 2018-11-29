package com.zerone.singleton;

/**
 * java中单例模式最好的方案,利用java语法，由java虚拟机保证单例，无并发问题
 * @author yxl
 * @since 2018/11/29
 */
public enum EnumImpl {
    instance;//相当于private static final EnumImpl instance = new EnumImpl();
    public void doSomething(){
        System.out.println("doSomething");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            System.out.println(EnumImpl.instance.hashCode());
        }
        EnumImpl.instance.doSomething();
    }
}
