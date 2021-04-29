package com.mgg.util;

public class LinkedList<T> {

    private Node<T> head;
    private int size;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * This method checks if the list is empty.
     * @return
     */
    public boolean isEmpty() {
        return (this.head == null);
    }

    /**
     * Gets the size of the list
     * @return
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Adds an element to the start of the list.
     * @param element the element you want to add to the start of the list.
     */
    public void addToStart(T element) {
        Node<T> newHead = new Node<T>(element);
        newHead.setNext(this.head);
        this.head = newHead;
        this.size++;
    }

    /**
     * Removes the element that is at the start of the list.
     */
    public void removeFromStart() {
        this.head = this.head.getNext();
        this.size--;
    }

    /**
     * Adds an element to the end of the list.
     * @param element the element you want to add to the end of the list.
     */
    public void addToEnd(T element) {
        this.addElementAtIndex(element, this.size);
    }

    /**
     * Removed the element at the end of the list.
     */
    public void removeFromEnd() {
        this.removeElementAtIndex(this.size);
    }

    /**
     * Adds an element to the desired index in the list.
     * @param element element you want to add
     * @param index index you want to place the element
     */
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

    /**
     * Removes an element at the desired index
     * @param index the index you want to have its element removed.
     */
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

    /**
     * Grabs the element at the index
     * @param index index you want to grab the element from in the list
     * @return the element you found at the index
     */
    public T getElementAtIndex(int index) {
        return this.getNodeAtIndex(index).getElement();
    }

    /**
     * Gets the node at the desired index in the list
     * @param index the place in the list(index)
     * @return The node that was found at the index
     */
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
}
