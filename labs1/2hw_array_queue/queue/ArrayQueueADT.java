package queue;

import java.util.Arrays;

public class ArrayQueueADT {
    private Object[] mas = new Object[8];
    private int first = 0, last = 0, size = 0;

    public static int size(ArrayQueueADT q) {
        return q.size;
    }

    public static boolean isEmpty(ArrayQueueADT q) {
        return q.size == 0;
    }

    public static void enqueue(ArrayQueueADT q, Object element) {
        assert element != null;
        ensureCapacity(q);
        q.size++;
        q.mas[q.last++] = element;
        q.last %= q.mas.length;
    }

    public static Object element(ArrayQueueADT q) {
        assert size(q) > 0;
        return q.mas[q.first];
    }

    public static Object dequeue(ArrayQueueADT q) {
        assert size(q) > 0;
        q.size--;
        Object result = q.mas[q.first++];
        q.first %= q.mas.length;
        return result;
    }

    private static void ensureCapacity(ArrayQueueADT q) {
        if (size(q) == q.mas.length - 1) {
            Object t[] = toArray(q);
            q.mas = Arrays.copyOf(t, q.mas.length * 2);
            q.first = 0;
            q.last = t.length;
        }
    }

    public static Object[] toArray(ArrayQueueADT q) {
        Object result[] = new Object[size(q)];
        for (int i = 0; i < result.length; i++) {
            result[i] = q.mas[(q.first + i) % q.mas.length];
        }
        return result;
    }

    public static String toStr(ArrayQueueADT q) {
        return Arrays.toString(toArray(q));
    }

    public static void clear(ArrayQueueADT q) {
        q.mas = new Object[8];
        q.first = 0;
        q.last = 0;
        q.size = 0;
    }

}