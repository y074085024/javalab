package com.zerone.dynamicproxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author yxl
 * @since 2018/11/22
 */
public class CglibProxy implements MethodInterceptor {

    public Object getProxy(Object target){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("pre handle");
        methodProxy.invokeSuper(o,objects);
        System.out.println("post handle");
        return null;
    }
}
