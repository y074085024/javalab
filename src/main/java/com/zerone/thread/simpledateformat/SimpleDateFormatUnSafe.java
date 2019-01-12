package com.zerone.thread.simpledateformat;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * SimpleDateFormat是线程不安全的，因为他有一个成员变量protected Calendar calendar;
 * 在进行format的时候这个calendar是线程共享的，所以会出现并发问题，可以使用以下四种方法解决并发问题
 * 将SimpleDateFormat声明为局部变量
 * 使用锁(synchronized/ReentrantLock/ReadWriteLock)同步共享的SimpleDateFormat变量
 * 使用ThreadLoal
 * 使用DateTimeFormatter代替SimpleDateFormat
 * @author yxl
 * @since 2019/1/12
 */
public class SimpleDateFormatUnSafe {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("test-pool-%d").build();

    private static ExecutorService pool = new ThreadPoolExecutor(5,200,0L,TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>(1024),threadFactory,new ThreadPoolExecutor.AbortPolicy());

    private static CountDownLatch countDownLatch = new CountDownLatch(100);

    public static void main(String[] args) throws InterruptedException {
        Set<String> dates = Collections.synchronizedSet(new HashSet<>());
        for(int i = 0;i<100;i++){
            Calendar calendar = Calendar.getInstance();
            LocalDateTime localDateTime = LocalDateTime.now();
            int finalI = i;
            pool.execute(()->{
                unsafe(dates, calendar, finalI);
//                safeWithSynchronized(dates, calendar, finalI);
//                safeWithLock(dates, calendar, finalI);
//                safeWithThreadLocal(dates, calendar, finalI);
//                safeWithDateTimeFormatter(dates, localDateTime, finalI);
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        System.out.println(dates.size());
        System.out.println(dates);
    }

    /**
     * 线程不安全的方式使用SimpleDateFormat
     * @param dates 日期字符串集合
     * @param calendar 当前时间
     * @param finalI 增加的天数
     */
    private static void unsafe(Set<String> dates, Calendar calendar, int finalI) {
        calendar.add(Calendar.DATE,finalI);
        String dateString = simpleDateFormat.format(calendar.getTime());
        dates.add(dateString);
    }

    /**
     * 使用synchronized方式进行线程同步
     * @param dates 日期字符串集合
     * @param calendar 当前时间
     * @param finalI 增加的天数
     */
    private static void safeWithSynchronized(Set<String> dates, Calendar calendar, int finalI) {
        calendar.add(Calendar.DATE,finalI);
        String dateString = null;
        //锁的粒度尽量小
        synchronized (simpleDateFormat){
            dateString = simpleDateFormat.format(calendar.getTime());
        }
        dates.add(dateString);
    }


    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    /**
     * 使用ReadWriteLock方式进行线程同步，读写锁粒度更小，性能好
     * @param dates 日期字符串集合
     * @param calendar 当前时间
     * @param finalI 增加的天数
     */
    private static void safeWithLock(Set<String> dates, Calendar calendar, int finalI) {
        calendar.add(Calendar.DATE,finalI);
        String dateString = null;
       try{
           lock.writeLock().lock();
           dateString = simpleDateFormat.format(calendar.getTime());
        }
        finally {
           lock.writeLock().unlock();
        }
        dates.add(dateString);
    }

    private static ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * 使用ThreadLocal方式进行线程区分，每个线程只能访问到自己堆栈内的SimpleDateFormat实例
     * @param dates 日期字符串集合
     * @param calendar 当前时间
     * @param finalI 增加的天数
     */
    private static void safeWithThreadLocal(Set<String> dates, Calendar calendar, int finalI) {
        calendar.add(Calendar.DATE,finalI);
        String dateString = null;
        dateString = sdf.get().format(calendar.getTime());
        dates.add(dateString);
    }

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");
    /**
     * DateTimeFormatter线程安全
     * @param dates 日期字符串集合
     * @param localDateTime 当前时间
     * @param finalI 增加的天数
     */
    private static void safeWithDateTimeFormatter(Set<String> dates, LocalDateTime localDateTime, int finalI) {
        String dateString = null;
        dateString = dtf.format(localDateTime.plusDays(finalI));
        dates.add(dateString);
    }
}
