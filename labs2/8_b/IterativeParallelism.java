package info.kgeorgiy.ja.aliev.mapper;

import info.kgeorgiy.java.advanced.concurrent.ScalarIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;


public class IterativeParallelism implements ScalarIP {

    private BaseParallelism baseParallelism;
    private ParallelMapper parallelMapper;


    public IterativeParallelism() {
        this.baseParallelism = new BaseParallelism();
    }

    public IterativeParallelism(ParallelMapper parallelMapper) {
        this.baseParallelism = new BaseParallelism(parallelMapper);
    }

    @Override
    public <T> T maximum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        return baseParallelism.distributeAndStart(threads, stream -> stream.max(comparator).orElseThrow(), values,
                stream -> stream.max(comparator).orElseThrow());
    }

    @Override
    public <T> T minimum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        return baseParallelism.distributeAndStart(threads, stream -> stream.min(comparator).orElseThrow(), values,
                stream -> stream.min(comparator).orElseThrow());
    }

    @Override
    public <T> boolean all(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return baseParallelism.distributeAndStart(threads, stream -> stream.allMatch(predicate), values,
                stream -> stream.allMatch((el) -> el.equals(true)));
    }

    @Override
    public <T> boolean any(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return baseParallelism.distributeAndStart(threads, stream -> stream.anyMatch(predicate), values,
                stream -> stream.anyMatch((el) -> el.equals(true)));
    }
}
