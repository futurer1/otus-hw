package ru.otus.gc.bench;


class Benchmark implements BenchmarkMBean {
    private final int loopCounter;
    private volatile int size = 0;
    private Object[] myArr;
    private int sleepMillis = 0;

    public Benchmark(int loopCounter, int sleepMillis) {
        this.loopCounter = loopCounter;
        this.myArr = new Object[loopCounter];
        this.sleepMillis = sleepMillis;
    }

    void run() throws InterruptedException {

        for (int idx = 0; idx < loopCounter; idx++) {
            int local = size;
            Object[] array = new Object[local];
            for (int i = 0; i < local; i++) {
                array[i] = new String(new char[0]);
                if (myArr[idx] == null && i > local/2) {
                    myArr[idx] = array;
                }
            }

            if (sleepMillis > 0) {
                Thread.sleep(sleepMillis);
            }
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        System.out.println("new size:" + size);
        this.size = size;
    }
}
