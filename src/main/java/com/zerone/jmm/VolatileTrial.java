package com.zerone.jmm;

/**
 * @author yxl
 * @since 2018/11/21
 */
public class VolatileTrial {
    private volatile static boolean exitFlag = false;

    public static void main(String[] args) {
        VolatileTrial volatileTrial = new VolatileTrial();

        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            volatileTrial.run();
        }).start();

        new Thread(()->{
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            volatileTrial.setExitFlag(true);
            System.out.println(volatileTrial.isExitFlag());
        }).start();


    }

    private void run(){
        System.out.println("start run...");
        while (!exitFlag){

        }
        System.out.println("exit...");
    }

    public boolean isExitFlag() {
        return exitFlag;
    }

    public void setExitFlag(boolean exitFlag) {
        VolatileTrial.exitFlag = exitFlag;
    }
}
