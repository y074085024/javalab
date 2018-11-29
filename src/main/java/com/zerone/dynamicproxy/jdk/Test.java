package com.zerone.dynamicproxy.jdk;

import java.lang.reflect.Proxy;

/**
 * @author yxl
 * @since 2018/11/22
 */
public class Test {
    public static void main(String[] args) {
        SubjectImpl subject = new SubjectImpl();
        Class cls = subject.getClass();
        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(subject);
        Subject2 proxy = (Subject2) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), jdkDynamicProxy);
        System.out.println("return "+proxy.doSomething2("doSomething2"));
        //proxy.doSomething("arg1",222);
    }
}
