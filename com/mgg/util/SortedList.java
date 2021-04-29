package com.mgg.util;

import java.util.Comparator;
import java.util.List;

public class SortedList<T> {

    private final LinkedList<T> underlyingList = new LinkedList<>();
    private final Comparator<T> cmp;

    public SortedList(Comparator<T> cmp) {
        this.cmp = cmp;
    }

    /**
     * Gets the size of the list
     * @return size of list in an integer form
     */
    public int size() {
        return this.underlyingList.getSize();
    }

    /**
     * Adds an element to the list, and places it accordingly depending on the comparator.
     * This is maintaining the list throughout adding, rather than doing it all at once at the end.
     * @param element element you want to add to the list.
     */
    public void add(T element) {
        if (this.underlyingList.isEmpty()) {
            this.underlyingList.addToStart(element);
        } else {
            boolean isInList = false;
            for (int i = 0; i < this.underlyingList.getSize(); i++) {
                int cmp = this.cmp.compare(element, this.underlyingList.getElementAtIndex(i));
                if (cmp < 1) { //0 & -1
                    this.underlyingList.addElementAtIndex(element, i);
                    isInList = true;
                    break;
                }
            }
            if (!isInList) {
                this.underlyingList.addToEnd(element);
            }
        }
    }

    /**
     * Adds the linked list and makes it sorted
     * @param list list of elements
     */
    public void batchAdd(List<T> list) {
        for (T element : list) {
            this.add(element);
        }
    }

    /**
     * Removes an element at an index.
     * @param index place in the list you want to remove.
     */
    public void remove(int index) {
        this.underlyingList.removeElementAtIndex(index);
    }

    /**
     * Grabs the element from the desired index.
     * @param index index you want to grab the element from in the list.
     * @return the element found at the index.
     */
    public T get(int index) {
        return this.underlyingList.getElementAtIndex(index);
    }
}