package ru.otus.gc.bench;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/*
О формате логов
http://openjdk.java.net/jeps/158


-Xms512m
-Xmx512m
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
-XX:+UseG1GC
*/

/*
1)
    default, time: 83 sec (82 without Label_1)
2)
    -XX:MaxGCPauseMillis=100000, time: 82 sec //Sets a target for the maximum GC pause time.
3)
    -XX:MaxGCPauseMillis=10, time: 91 sec

4)
-Xms2048m
-Xmx2048m
    time: 81 sec

5)
-Xms5120m
-Xmx5120m
    time: 80 sec

5)
-Xms20480m
-Xmx20480m
    time: 81 sec (72 without Label_1)

*/

public class GcDemo {

    private static int countYoung = 0;
    private static int countOld = 0;
    private static int timeYoung = 0;
    private static int timeOld = 0;

    public static void main(String... agrs) throws Exception {

        var obj = new GcDemo();

        // G1 Garbage Collector
        // 1. obj.run(10000, 100000, 0, "Young", "Old");
        // -Xms512m -Xmx512m -XX:+UseG1GC -XX:MaxGCPauseMillis=10

        // 2. obj.run(10000, 100000, 10, "Young", "Old");
        // -Xms512m -Xmx512m -XX:+UseG1GC -XX:MaxGCPauseMillis=10

        // 3. obj.run(10000, 100000, 100, "Young", "Old");
        // -Xms512m -Xmx512m -XX:+UseG1GC -XX:MaxGCPauseMillis=10

        // 4. obj.run(10000, 100000, 100, "Young", "Old");
        // -Xms512m -Xmx512m -XX:+UseG1GC -XX:MaxGCPauseMillis=100

        // 5. obj.run(10000, 100000, 0, "Young", "Old");
        // -Xms4096m -Xmx4096m -XX:+UseG1GC -XX:MaxGCPauseMillis=10

        // 6. obj.run(10000, 100000, 10, "Young", "Old");
        // -Xms4096m -Xmx4096m -XX:+UseG1GC -XX:MaxGCPauseMillis=10


        // Parallel Garbage Collector
        // 1. obj.run(10000, 100000, 0, "Scavenge", "MarkSweep");
        // -Xms512m<br> -Xmx512m<br> -XX:+UseParallelGC<br> -XX:MaxGCPauseMillis=10

        // 2. obj.run(10000, 100000, 10, "Scavenge", "MarkSweep");
        // -Xms512m<br> -Xmx512m<br> -XX:+UseParallelGC<br> -XX:MaxGCPauseMillis=10

        // 3. obj.run(10000, 100000, 100, "Scavenge", "MarkSweep");
        // -Xms512m<br> -Xmx512m<br> -XX:+UseParallelGC<br> -XX:MaxGCPauseMillis=10

        // 4. obj.run(10000, 100000, 100, "Scavenge", "MarkSweep");
        // -Xms512m<br> -Xmx512m<br> -XX:+UseParallelGC<br> -XX:MaxGCPauseMillis=100

        // 5. obj.run(10000, 100000, 0, "Scavenge", "MarkSweep");
        // -Xms4096m -Xmx4096m -XX:+UseParallelGC -XX:MaxGCPauseMillis=10

        // 6. obj.run(10000, 100000, 10, "Scavenge", "MarkSweep");
        // -Xms4096m -Xmx4096m -XX:+UseParallelGC -XX:MaxGCPauseMillis=10
    }

    public void run(int sizeBatch, int loopCount, int sleepMillis, String youngAttribute, String oldAttribute) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring(youngAttribute, oldAttribute);
        long beginTime = System.currentTimeMillis();

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");

        ru.otus.gc.bench.Benchmark mbean = new ru.otus.gc.bench.Benchmark(loopCount, sleepMillis);
        mbs.registerMBean(mbean, name);
        mbean.setSize(sizeBatch);
        mbean.run();

        System.out.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);
    }

    private static void switchOnMonitoring(String youngAttribute, String oldAttribute) {
        List<GarbageCollectorMXBean> gcbeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");

                    if (gcName.contains(youngAttribute)) {
                        countYoung++;
                        timeYoung += duration;
                    }
                    if (gcName.contains(oldAttribute)) {
                        countOld++;
                        timeOld += duration;
                    }
                    System.out.println("Young - " + countYoung + " ( " + timeYoung + " ms); Old - " + countOld + " (" + timeOld + " ms)");
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
