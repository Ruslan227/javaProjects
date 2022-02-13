package queue;

import java.util.Arrays;

abstract public class AbstractQueue implements Queue {


    abstract protected void doEnqueue(Object element);

    abstract protected Object doElement();

    abstract protected void doRemove();

    public void enqueue(Object element) {
        assert element != null;
        doEnqueue(element);

    }

    public Object element() {
        assert size() > 0;
        return doElement();
    }

    public Object dequeue() {
        assert size() > 0;
        Object res = element();
        doRemove();
        return res;
    }

    public Object[] toArray() {
        Object result[] = new Object[size()];
        for (int i = 0; i < size(); i++) {
            result[i] = element();
            enqueue(dequeue());
        }
        return result;
    }


    public boolean isEmpty() {
        return size() == 0;
    }

    public String toStr() {
        return Arrays.toString(toArray());
    }
}