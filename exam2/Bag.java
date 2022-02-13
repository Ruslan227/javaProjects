package info.kgeorgiy.ja.aliev;

import java.util.*;
import java.util.function.Function;

public class Bag<T> extends AbstractCollection<T> {

    private final Map<T, Deque<T>> elements = new HashMap<>();
    private final Function<T, Deque<T>> initializer = (T key) -> new ArrayDeque<>();
    private int size = 0;

    public Bag() {
    }
    // :NOTE: * <? extends T> ?
    public Bag(final Collection<T> coll) {
        addAll(coll);
    }

    @Override
    public boolean add(final T e) {
        elements.computeIfAbsent(e, initializer);
        elements.get(e).add(e);
        size++;
        return true;
    }

    // :NOTE: * addAll и другие *All не нужно реализовывать, они уже есть в AbstractCollection
    @Override
    public boolean addAll(final Collection<? extends T> coll) {
        coll.forEach(this::add);
        return true;
    }

    @Override
    public void clear() {
        elements.clear();
        size = 0;
    }

    @SuppressWarnings("all")
    @Override
    public boolean contains(final Object o) {
        return elements.containsKey(o);
    }

    @SuppressWarnings("all")
    @Override
    public boolean containsAll(final Collection<?> c) {
        final Bag<?> bag = new Bag<>(c);
        for (final var pair : bag.elements.entrySet()) {
            final Deque<T> deque = elements.getOrDefault(pair.getKey(), null);
            if (deque == null || deque.size() < pair.getValue().size()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new BagIterator();
    }

    @SuppressWarnings("all")
    @Override
    public boolean remove(final Object o) {
        final Deque<T> deque = elements.getOrDefault(o, null);
        if (deque == null) {
            return false;
        }
        deque.pop();
        if (deque.size() == 0) {
            elements.remove(o);
        }
        size--;
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        final int startSize = size();
        c.forEach(this::remove);
        return startSize - size() == c.size();
    }

    @SuppressWarnings("all")
    @Override
    public boolean retainAll(final Collection<?> c) {
        final Bag<T> bag = new Bag<>(this);
        bag.removeAll(c);
        return removeAll(bag);
    }

    @Override
    public int size() {
        return size;
    }

    // :NOTE: * toArray реализован в Abstract
    @Override
    public Object[] toArray() {
        return elements.values()
                .stream()
                .flatMap(Collection::stream)
                .toArray();
    }

    @SuppressWarnings("all")
    @Override
    public <U> U[] toArray(final U[] a) {
        final Object[] ar = toArray();
        System.arraycopy(ar, 0, a, 0, Math.min(a.length, ar.length));
        return a;
    }

    private class BagIterator implements Iterator<T> {
        private final Iterator<Deque<T>> it;
        private Iterator<T> curDequeIt;
        private Deque<T> curDeque;

        BagIterator() {
            it = elements.values().iterator();
            if (it.hasNext()) {
                curDeque = it.next();
                curDequeIt = curDeque.iterator();
            }
        }

        @Override
        public boolean hasNext() {
            if (curDequeIt == null) {
                return false;
            }
            return curDequeIt.hasNext() || it.hasNext();
        }

        @Override
        public T next() {
            if (!curDequeIt.hasNext() && it.hasNext()) {
                curDeque = it.next();
                curDequeIt = curDeque.iterator();
            }
            return curDequeIt.next();
        }

        @Override
        public void remove() {
            curDequeIt.remove();
            if (curDeque.size() == 0) {
                it.remove();
            }
        }
    }

}

