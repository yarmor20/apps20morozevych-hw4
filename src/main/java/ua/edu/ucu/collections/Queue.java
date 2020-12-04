package ua.edu.ucu.collections;


public class Queue {
    private ImmutableLinkedList list = null;
    private int size;

    public Queue(Object e) {
        list = new ImmutableLinkedList(e);
    }

    public Queue() {
    }

    public void enqueue(Object e) {
        if (list == null) {
            list = new ImmutableLinkedList(e);
        } else {
            list = list.addLast(e);
        }
        this.size++;
    }

    public Object dequeue() {
        Object toBeRemoved = list.getFirst();
        list = list.removeFirst();
        this.size--;
        return toBeRemoved;
    }

    public Object peek() {
        return list.getFirst();
    }

    public int size() {
        return this.size;
    }
}
