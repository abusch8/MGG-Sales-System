package com.mgg.util;

import java.util.Comparator;
import java.util.List;

public class SortedList<T> {

    private final LinkedList<T> underlyingList = new LinkedList<>();
    private final Comparator<T> cmp;

    public SortedList(Comparator<T> cmp) {
        this.cmp = cmp;
    }

    public int size() {
        return this.underlyingList.getSize();
    }

    public void add(T element) {
        for (int i = 0; i < this.underlyingList.getSize(); i++) {
            if (this.cmp.compare(element, this.underlyingList.getElementAtIndex(i)) >= 0) {
                this.underlyingList.addElementAtIndex(element, i);
                break;
            }
        }
    }

    public void batchAdd(List<T> list) {
        for (T element : list) {
            this.add(element);
        }
    }

    public void remove(int index) {
        this.underlyingList.removeElementAtIndex(index);
    }

    public T get(int index) {
        return this.underlyingList.getElementAtIndex(index);
    }

    public String toString() {
        return this.underlyingList.toString();
    }

    public boolean isEmpty() {
        return this.underlyingList.isEmpty();
    }
}