package info.kgeorgiy.ja.aliev.mapper;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;



import static java.lang.Thread.interrupted;

public class ParallelMapperImpl implements ParallelMapper {
    private final List<Thread> threadList;
    private final Queue<Task> taskQueue;
    private Counter counter;




    public ParallelMapperImpl(int threadsAmount) {
        this.counter = new Counter(0);
        this.taskQueue = new ArrayDeque<>();
        this.threadList = new ArrayList<>();

        for (int i = 0; i < threadsAmount; i++) {

            Thread thread = new Thread(() -> {
                try {
                    while (!interrupted()) {
                        processTask();
                    }
                } catch (InterruptedException e) {
                    // ignored
                }
            });

            thread.start();
            threadList.add(thread);
        }
    }


    private void processTask() throws InterruptedException {
        Task task;
        synchronized (taskQueue) {
            while (taskQueue.isEmpty()) {
                this.taskQueue.wait();
            }
            task = this.taskQueue.poll();
        }
        task.run();
    }


    private void addTask(Task task) {
        synchronized (taskQueue) {
            taskQueue.add(task);
            taskQueue.notify();
        }
    }


    @Override
    public <T, R> List<R> map(Function<? super T, ? extends R> f, List<? extends T> args) throws InterruptedException {
        final List<R> mapResult = new ArrayList<>(Collections.nCopies(args.size(), null));
        for (int i = 0; i < args.size(); i++) {
            final int ind = i;
            addTask(new Task(() -> mapResult.set(ind, f.apply(args.get(ind))), counter));
        }
        counter.waitUntilNotEqual(args.size());
        return mapResult;
    }

    @Override
    public void close() {
        for (Thread thread : threadList) {
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                // ignored
            }
        }
    }


}
