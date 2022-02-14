package info.kgeorgiy.ja.aliev.mapper;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;


public class BaseParallelism {
    private ParallelMapper parallelMapper;

    public BaseParallelism(ParallelMapper parallelMapper) {
        this.parallelMapper = parallelMapper;
    }

    public BaseParallelism() {
        this.parallelMapper = null;
    }

    private <T> void validate(List<T> list, final int threads) throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(list);
        if (threads < 1) throw new IllegalArgumentException("threads amount should be > 0");
    }





//    public <T, R> R distributeAndStart(int threads,
//                                       Function<Stream<? extends T>, R> function,
//                                       List<? extends T> list,
//                                       Function<Stream<? extends R>, R> resultFunction) throws InterruptedException {
//
//        validate(list, threads);
//        threads = Math.min(threads, list.size());
//        int threadPortion = list.size() / threads;
//        int leftovers = list.size() % threads;
//        List<R> threadResults = new ArrayList<>(Collections.nCopies(threads, null));
//        List<Thread> threadList = new ArrayList<>();
//
//        for (int i = 0, ind = 0, add; i < threads; i++) {
//            add = threadPortion + ((leftovers <= 0) ? 0 : 1);
//            if (add == 0) break;
//            List<? extends T> portion = list.subList(ind, ind + add);
//            ind += add;
//            leftovers--;
//            int insertPosition = i;
//
//            threadList.add(new Thread(() -> threadResults.set(insertPosition, function.apply(portion.stream()))));
//            threadList.get(i).start();
//        }
//
//        try {
//            joinAll(threadList);
//        } catch (InterruptedException e) {
//            System.out.println(e.getMessage());
//            throw e;
//        }
//        return resultFunction.apply(threadResults.stream());
//    }





    public <T, R> R distributeAndStart(int threads,
                                       Function<Stream<? extends T>, R> function,
                                       List<? extends T> list,
                                       Function<Stream<? extends R>, R> resultFunction) throws InterruptedException {

        validate(list, threads);
        threads = Math.min(threads, list.size());
        int threadPortion = list.size() / threads;
        int leftovers = list.size() % threads;

        List<List<? extends T>> taskList = new ArrayList<>();

        if (parallelMapper == null) {
            parallelMapper = new ParallelMapperImpl(threads);   //
        }

        for (int i = 0, ind = 0, add; i < threads; i++) {
            add = threadPortion + ((leftovers <= 0) ? 0 : 1);
            if (add == 0) break;
            taskList.add(list.subList(ind, ind + add));
            ind += add;
            leftovers--;
        }

        R res = resultFunction.apply(parallelMapper.map((task) -> function.apply(task.stream()), taskList).stream());

//        parallelMapper.close();
        return res;
    }





//
//    private void joinAll(final List<Thread> threadList) throws InterruptedException {
//        InterruptedException interruptedException = null;
//        for (Thread thread : threadList) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                if (interruptedException == null)
//                    interruptedException = new InterruptedException("thread was interrupted");
//                else
//                    interruptedException.addSuppressed(e);
//            }
//        }
//        if (interruptedException != null)
//            throw interruptedException;
//    }

}




















