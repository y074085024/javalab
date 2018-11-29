package com.zerone.dynamicproxy.jdk;

/**
 * @author yxl
 * @since 2018/11/22
 */
public class SubjectImpl implements Subject,Subject2 {

    @Override
    public void doSomething() {
        System.out.println("doSomething...");
    }

    @Override
    public void doSomething(String arg1, int arg2) {
        System.out.println("doSomething..." + arg1 + "," + arg2);
    }

    @Override
    public String doSomething2(String s) {
        System.out.println(s);
        return s;
    }
}
