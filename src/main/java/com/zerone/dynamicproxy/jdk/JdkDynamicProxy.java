package com.zerone.dynamicproxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author yxl
 * @since 2018/11/22
 */
public class JdkDynamicProxy implements InvocationHandler {

    private Object o;
    public JdkDynamicProxy(Object o){
        this.o = o;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("pre handle");
        Object result = method.invoke(o,args);
        System.out.println("post handle");
        return result;
    }
}
