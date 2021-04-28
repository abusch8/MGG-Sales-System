package com.mgg.util;

public class LinkedList<T> {

    private Node<T> head;
    private int size;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return (this.head == null);
    }

    public int getSize() {
        return this.size;
    }

    public void addToStart(T x) {
        Node<T> newHead = new Node<T>(x);
        newHead.setNext(this.head);
        this.head = newHead;
        this.size++;
    }

    public void addToEnd(T x) {
        this.addElementAtIndex(x, this.size);
    }

    public void addElementAtIndex(T x, int index) {
        if (index < 0 || index > size) {
            throw  new IllegalArgumentException("index " + index + " is out of bounds.\n");
        }
        if (this.getSize() == 0 || index == 0) {
            addToStart(x);
        } else {
            Node<T> previous = getNodeAtIndex(index-1);
            Node<T> current = previous.getNext();
            Node<T> newNode = new Node<>(x);
            newNode.setNext(current);
            previous.setNext(newNode);
            this.size++;
        }
    }

    public Node<T> getNodeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw  new IllegalArgumentException("index " + index + " is out of bounds.\n");
        }
        Node<T> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current;
    }

    public T getElementAtIndex(int index) {
        return this.getNodeAtIndex(index).getElement();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> current = this.head;
        while (current.getNext() != null) {
            sb.append(current.getElement() + ", ");
            current = current.getNext();
        }
        sb.append(current.getElement());
        return sb.toString();
    }

}
