package queue;

import java.util.Arrays;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[16];
    private int first = 0, last = 0, size = 0;

    public int size() {
        return size;
    }

    protected void doEnqueue(Object element) {
        ensureCapacity();
        elements[last++] = element;
        size++;
        last %= elements.length;
    }

    protected Object doElement() {
        return elements[first];
    }

    protected void doRemove() {
        first = (first + 1) % elements.length;
        size--;
    }

    public void clear() {
        elements = new Object[16];
        first = 0;
        last = 0;
        size = 0;
    }

    private void ensureCapacity() {
        if (size() == elements.length - 1) {
            Object tmp[] = toArray();
            elements = Arrays.copyOf(tmp, tmp.length * 2);
            first = 0;
            last = tmp.length;
        }
    }
}