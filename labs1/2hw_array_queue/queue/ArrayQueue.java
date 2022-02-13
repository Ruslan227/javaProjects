package queue;

import java.util.Arrays;

// Inv:  n >= 0; q[0..i-1] != null
public class ArrayQueue {
    private Object[] mas = new Object[8];
    private int first = 0, last = 0, size = 0;

    // Pre: none
    // Post: return size of queue, elements is immutable
    public int size() {
        return size;
    }

    // Pre: none
    // Post: does what you think, elements is immutable
    public boolean isEmpty() {
        return size == 0;
    }

    // Pre: none
    // Post: add element in the position 'tail' and changes tail, size (increments).
    // (add element to the end of queue)

    public void enqueue(Object element) {
        assert element != null;
        ensureCapacity();
        size++;
        mas[last++] = element;
        last %= mas.length;
    }

    // Pre: size() > 0
    // Post: returns first element of queue (element at the 'head' position) &&
    // elements is immutable
    public Object element() {
        assert size() > 0;
        return mas[first];
    }

    // Pre: n > 0
    // Post: R = q[0]; n' = n -1; q'[0..n'-1]=q[1..n-1]
    public Object dequeue() {
        assert size() > 0;
        size--;
        Object result = mas[first++];
        first %= mas.length;
        return result;
    }

    // Pre: none
    // Post: doubles the size of elemens array, set elements in queue in "correct"
    // order from head to tail, but from 0 index of array
    // set head = 0 && tail = size of queue
    private void ensureCapacity() {
        if (size() == mas.length - 1) {
            Object t[] = toArray();
            mas = Arrays.copyOf(t, mas.length * 2);
            first = 0;
            last = t.length;
        }
    }

    // Pre: none
    // Post: returns array of queue elements from first to last && elements is
    // immutable
    public Object[] toArray() {
        Object result[] = new Object[size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = mas[(first + i) % mas.length];
        }
        return result;
    }

    // Pre: none
    // Post: same as toArray, but gives String with square brackets at the beginning
    // and the end
    public String toStr() {
        return Arrays.toString(toArray());
    }

    // Pre: None
    // Post: reset array elements, and head, tail, size values ;
    public void clear() {
        mas = new Object[8];
        first = 0;
        last = 0;
        size = 0;
    }
}