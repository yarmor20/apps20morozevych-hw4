package ua.edu.ucu.collections;


public class ImmutableLinkedList implements ImmutableList{
    private final Node head;
    private int length = 0;

    public ImmutableLinkedList(Object e) {
        head = new Node(e);
        length++;
    }

    private ImmutableLinkedList(Node newHead, int newLength) {
        head = newHead;
        length = newLength;
    }

    private Node cloneNodes() {
        Node newHead = new Node(head.data);

        if (length == 1) {
            return newHead;
        }

        Node currIterator = newHead;
        Node currentNode = head;

        for (int i = 1; i < length; i++) {
            currIterator.next = new Node(currentNode.next.data);
            currentNode = currentNode.next;
            currIterator = currIterator.next;
        }
        return newHead;
    }

    private Node getTail(Node newHead) {
        Node currentNode = newHead;
        for (int i = 1; i < length; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    public ImmutableLinkedList addFirst(Object e) {
        Node newHead = cloneNodes();
        Node firstElem = new Node(e);
        firstElem.next = newHead;

        return new ImmutableLinkedList(firstElem, length + 1);
    }

    public ImmutableLinkedList addLast(Object e) {
        return (ImmutableLinkedList) add(e);
    }

    public Object getFirst() {
        return head.data;
    }

    public Object getLast() {
        return getTail(head).data;
    }

    public ImmutableLinkedList removeFirst() {
        Node newHead = cloneNodes();
        newHead = newHead.next;
        return new ImmutableLinkedList(newHead, length - 1);
    }

    public ImmutableLinkedList removeLast() {
        Node newHead = cloneNodes();

        Node currentNode = newHead;
        for(int i = 1; i < length - 1; i++) {
            currentNode = currentNode.next;
        }
        currentNode.next = null;
        return new ImmutableLinkedList(newHead, length - 1);
    }

    @Override
    public ImmutableList add(Object e) {
        Node newHead = cloneNodes();
        Node newTail = getTail(newHead);

        newTail.next = new Node(e);
        return new ImmutableLinkedList(newHead, length + 1);
    }

    @Override
    public ImmutableList add(int index, Object e) throws IndexOutOfBoundsException{
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }
        Node newHead = cloneNodes();
        Node currentNode = newHead;

        if (index == 0) {
            newHead = new Node(e);
            newHead.next = currentNode;
        }
        else {
            for(int i = 1; i < index; i++) {
                currentNode = currentNode.next;
            }
            Node previousNode = currentNode.next;

            currentNode.next = new Node(e);
            currentNode.next.next = previousNode;
        }
        return new ImmutableLinkedList(newHead, length + 1);
    }

    @Override
    public ImmutableList addAll(Object[] c) {
        Node newHead = cloneNodes();
        Node newTail = getTail(newHead);

        for(Object obj: c) {
            newTail.next = new Node(obj);
            newTail = newTail.next;
        }
        return new ImmutableLinkedList(newHead, length + c.length);
    }

    @Override
    public ImmutableList addAll(int index, Object[] c) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }
        Node newHead = cloneNodes();

        Node firstEntry = new Node(c[0]);
        Node currNode = firstEntry;
        for (int i = 1; i < c.length; i++) {
            currNode.next = new Node(c[i]);
            currNode = currNode.next;
        }

        if (index == 0) {
            Node nextNode = newHead;
            newHead = firstEntry;
            currNode.next = nextNode;
            return new ImmutableLinkedList(newHead, length + c.length);
        }

        Node currentNode = newHead;
        for(int i = 1; i < index; i++) {
            currentNode = currentNode.next;
        }
        Node nextNode = currentNode.next;

        currentNode.next = firstEntry;
        currNode.next = nextNode;

        return new ImmutableLinkedList(newHead, length + c.length);
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }

        Node currentNode = head;
        for (int i = 1; i <= index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.data;
    }

    @Override
    public ImmutableList remove(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }

        Node newHead = cloneNodes();
        if (index == 0) {
            newHead = newHead.next;
            return new ImmutableLinkedList(newHead, length - 1);
        }

        Node currentNode = newHead;
        for (int i = 1; i < index; i++) {
            currentNode = currentNode.next;
        }

        Node toBeDeleted = currentNode.next;
        currentNode.next = toBeDeleted.next;
        return new ImmutableLinkedList(newHead, length - 1);
    }

    @Override
    public ImmutableList set(int index, Object e) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }
        Node newHead = cloneNodes();
        Node currentNode = newHead;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        currentNode.data = e;
        return new ImmutableLinkedList(newHead, length);
    }

    @Override
    public int indexOf(Object e) {
        Node currentNode = head;
        for (int i = 0; i < length; i++) {
            if (currentNode.data == e) {
                return i;
            }
            currentNode = currentNode.next;
        }
        return -1;
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public ImmutableList clear() {
        return new ImmutableLinkedList(null, 0);
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[length];

        Node currentNode = head;
        for(int i = 0; i < length; i++) {
            array[i] = currentNode.data;
            currentNode = currentNode.next;
        }

        return array;
    }
}

class Node {
    public Object data;
    public Node next = null;

    Node(Object d) { data = d; }
}
