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

    public void addToStart(T element) {
        Node<T> newHead = new Node<T>(element);
        newHead.setNext(this.head);
        this.head = newHead;
        this.size++;
    }

    public void removeFromStart() {
        this.head = this.head.getNext();
        this.size--;
    }

    public void addToEnd(T element) {
        this.addElementAtIndex(element, this.size);
    }

    public void removeFromEnd() {
        this.removeElementAtIndex(this.size);
    }

    public void addElementAtIndex(T element, int index) {
        if (index < 0 || index > this.size) {
            throw new IllegalArgumentException("index " + index + " is out of bounds.\n");
        }
        if (this.isEmpty() || index == 0) {
            addToStart(element);
        } else {
            Node<T> previous = getNodeAtIndex(index - 1);
            Node<T> current = previous.getNext();
            Node<T> newNode = new Node<>(element);
            newNode.setNext(current);
            previous.setNext(newNode);
            this.size++;
        }
    }

    public void removeElementAtIndex(int index) {
        if (index < 0 || index > this.size) {
            throw new IllegalArgumentException("index " + index + " is out of bounds.\n");
        }
        if (this.isEmpty() || index == 0) {
            removeFromStart();
        } else {
            Node<T> previous = getNodeAtIndex(index - 1);
            Node<T> current = previous.getNext();
            previous.setNext(current.getNext());
            this.size--;
        }
    }

    public T getElementAtIndex(int index) {
        return this.getNodeAtIndex(index).getElement();
    }

    private Node<T> getNodeAtIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IllegalArgumentException("index " + index + " is out of bounds.\n");
        }
        Node<T> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current;
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
