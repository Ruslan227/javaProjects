package queue;

import java.util.Arrays;

public class ArrayQueueModule {
    private static Object[] mas = new Object[8];
    private static int first = 0, last = 0, size = 0;

    public static int size() {
        return size;
    }

    public static boolean isEmpty() {
        return size == 0;
    }

    public static void enqueue(Object element) {
        assert element != null;
        ensureCapacity();
        size++;
        mas[last++] = element;
        last %= mas.length;
    }

    public static Object element() {
        assert size() > 0;
        return mas[first];
    }

    public static Object dequeue() {
        assert size() > 0;
        size--;
        Object result = mas[first++];
        first %= mas.length;
        return result;
    }

    private static void ensureCapacity() {
        if (size() == mas.length - 1) {
            Object[] t = toArray();
            mas = Arrays.copyOf(t, mas.length * 2);
            first = 0;
            last = t.length;
        }
    }

    public static Object[] toArray() {
        Object[] result = new Object[size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = mas[(first + i) % mas.length];
        }
        return result;
    }

    public static String toStr() {
        return Arrays.toString(toArray());
    }

    public static void clear() {
        mas = new Object[8];
        first = 0;
        last = 0;
        size = 0;
    }

}