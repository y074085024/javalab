package com.zerone.singleton;

/**
 * 懒汉式，双重检验锁(DCL)
 * 由于instance=new LazyDoubleCheckImpl()实际上被分解为
 * mem = allocate();                  //为单例对象分配内存空间.
 * instance = mem;                    //注意，instance 引用现在是非空，但还未初始化
 * constructorSingleton(instance);    //为单例对象通过instance调用构造函数
 * 所以在线程并发时其他线程可能使用的是已经分配好内存空间但是还未初始化成功的对象引用
 * 导致在java1.4以前版本存在一定的bug，由于java1.5对于volatile重新定义，保证了对象的成员变量初始化完成，才能执行后续操作，保证有序性
 * @author yxl
 * @since 2018/11/29
 */
public class LazyDoubleCheckImpl {
    private volatile static LazyDoubleCheckImpl instance = null;

    private LazyDoubleCheckImpl() {

    }

    public static LazyDoubleCheckImpl getInstance() {
        if(instance==null) {
            synchronized (LazyDoubleCheckImpl.class) {
                if(instance==null)
                    instance = new LazyDoubleCheckImpl();
            }
        }
        return instance;
    }
}
