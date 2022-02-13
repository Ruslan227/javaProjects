package queue;

public class LinkedQueue extends AbstractQueue {
    private class Node {
        Node next = null, prev = null;
        Object data = null;
    }

    private Node first = null, last = null;
    private int size = 0;

    public int size() {
        return size;
    }

    protected void doEnqueue(Object element) {
        Node newElement = new Node();
        newElement.data = element;
        if (isEmpty()) {
            first = newElement;
            last = newElement;
        } else {
            last.next = newElement;
            newElement.prev = last;
            last = newElement;
        }
        size++;
    }

    protected void doRemove() {
        if (first.next != null) {
            first = first.next;
            first.prev = null;
        } else {
            first = null;
        }
        size--;
    }

    protected Object doElement() {
        return first.data;
    }

    public void clear() {
        first = null;
        last = null;
        size = 0;
    }
}