package com.zerone.singleton;

/**
 * 对于懒汉式双重检验锁使用threadlocal修复
 *
 * happen-before规则，利用它们可以确定两个操作之间是否存在happen-before关系。
 *
 * 1.同一个线程中，书写在前面的操作happen-before书写在后面的操作。这条规则是说，在单线程 中操作间happen-before关系完全是由源代码的顺序决定的，
 * 这里的前提“在同一个线程中”是很重要的，这条规则也称为单线程规则 。这个规则多少说得有些简单了，考虑到控制结构和循环结构，
 * 书写在后面的操作可能happen-before书写在前面的操作，不过我想读者应该明白我的意思。
 * 2.对锁的unlock操作happen-before后续的对同一个锁的lock操作。这里的“后续”指的是时间上的先后关系，unlock操作发生在退出同步块之后，
 * lock操作发生在进入同步块之前。这是条最关键性的规则，线程安全性主要依赖于这条规则。但是仅仅是这条规则仍然不起任何作用，
 * 它必须和下面这条规则联合起来使用才显得意义重大。这里关键条件是必须对“同一个锁”的lock和unlock。
 * 3.如果操作A happen-before操作B，操作B happen-before操作C，那么操作A happen-before操作C。这条规则也称为传递规则。
 * @author yxl
 * @since 2018/11/29
 */
public class LazyDoubleCheckFixImpl {
        private static final ThreadLocal perThreadInstance = new ThreadLocal();
        private static LazyDoubleCheckFixImpl singleton ;
        private LazyDoubleCheckFixImpl() {}

        public static LazyDoubleCheckFixImpl  getInstance() {
            if (perThreadInstance.get() == null){
                // 每个线程第一次都会调用
                createInstance();
            }
            return singleton;
        }

        private static  final void createInstance() {
            synchronized (LazyDoubleCheckFixImpl.class) {
                if (singleton == null){
                    singleton = new LazyDoubleCheckFixImpl();
                }
            }
            perThreadInstance.set(perThreadInstance);
        }
}
