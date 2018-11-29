package com.zerone.singleton;

/**
 * 私有静态内部类实现，InstanceHolder在使用的时候才加载，初始化静态变量，jvm(Classloader)保证线程安全
 * @author yxl
 * @since 2018/11/29
 */
public class InnerClassImpl {
    /*
        收回外部实例化权限
     */
    private InnerClassImpl(){}

    private static class InstanceHolder {
        public static InnerClassImpl instance = new InnerClassImpl();
    }

    public static InnerClassImpl getInstance(){
        return InstanceHolder.instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            System.out.println(InnerClassImpl.getInstance().hashCode());
        }
    }
}
