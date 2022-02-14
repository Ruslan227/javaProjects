package info.kgeorgiy.ja.aliev.mapper;

public class Counter {
    private int cnt;
    private Counter counterObj;

    public int getCnt() {
        return cnt;
    }

    public Counter(int cnt) {
        this.cnt = 0;
    }

    public Counter(Counter obj) {
        counterObj = obj;
    }

    public synchronized void increment() {
        this.cnt++;
        counterObj = this;
        notify();
    }


    public synchronized void waitUntilNotEqual(int val2) throws InterruptedException {
        while (this.cnt < val2) {
            this.wait();
        }
        notify();
    }
}
