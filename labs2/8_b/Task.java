package info.kgeorgiy.ja.aliev.mapper;

import static java.lang.Thread.currentThread;

public class Task implements Runnable {
    private boolean finished;
    private final Runnable work;
    public Counter counter;


    public Task(Runnable work, Counter counter) {
        this.work = work;
        this.finished = false;
        this.counter = counter;
    }

    public boolean isFinished() {
        return finished;
    }

//    public void setFinished(boolean finished) {
//        this.finished = finished;
//    }

    @Override
    public void run() {
        this.work.run();
        this.finished = true;
        counter.increment();
    }
}
