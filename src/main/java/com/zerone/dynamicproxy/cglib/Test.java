package com.zerone.dynamicproxy.cglib;

/**
 * @author yxl
 * @since 2018/11/22
 */
public class Test {
    public static void main(String[] args) {
        Person person = new Person();
        CglibProxy cglibProxy = new CglibProxy();
        Person proxy = (Person) cglibProxy.getProxy(person);
        proxy.doSomething();
    }
}
