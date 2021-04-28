package com.mgg;

import java.util.Comparator;

public class SortedList<Sale> {

    private final LinkedList<Sale> underlyingList = new LinkedList<>();
    private final Comparator<Sale> cmp;

    public SortedList(Comparator<Sale> cmp) {
        this.cmp = cmp;
    }

    public void add(Sale s) {
        for (int i = 0; i < underlyingList.getSize(); i++) {
            if (cmp.compare(s, underlyingList.getElementAtIndex(i)) >= 1) {
                underlyingList.addElementAtIndex(s, i);
            }
        }
    }
}