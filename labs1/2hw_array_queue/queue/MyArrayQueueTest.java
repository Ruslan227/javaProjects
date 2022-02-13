package queue;

import java.util.Arrays;

public class MyArrayQueueTest {
    public static void fill(ArrayQueue queue) {
        for (int i = 0; i < 32; i++) {
            queue.enqueue(i);
        }
    }

    public static void dump(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " + queue.element() + " " + queue.dequeue()
             + "  " + queue.toStr());
        }
    }



    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue();
        fill(queue);
        dump(queue);
    }
}