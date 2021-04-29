package com.mgg.util;

import com.mgg.entity.Sale;

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
        if (this.underlyingList.isEmpty()) {
            this.underlyingList.addToStart(element);

            System.out.println(((Sale)element).getSaleCode());

        } else {
            for (int i = 0; i < this.underlyingList.getSize(); i++) {
                int cmp = this.cmp.compare(element, this.underlyingList.getElementAtIndex(i));
//                if (cmp < 1) { //
//                    if (i == 0) {
//
//                    }
//                } else {
//                    this.underlyingList.addElementAtIndex(element, i);
//                    break;
//                }
//
                if (cmp < 1) { //0 & -1
                    this.underlyingList.addElementAtIndex(element, i);
                    System.out.println(((Sale)element).getSaleCode());
                    break;
                }
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
}