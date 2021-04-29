package com.mgg.io;

import com.mgg.entity.Sale;
import com.mgg.util.SortedList;

import java.util.Comparator;
import java.util.List;

public class PrintSortedLists {

    public static void printList(List<Sale> sales) {

        Comparator<Sale> cmpByCustomerName = (a, b) -> {
            int cmp = CharSequence.compare(a.getCustomer().getLastName(), b.getCustomer().getLastName());
            if (cmp == 0) {
                cmp = CharSequence.compare(a.getCustomer().getFirstName(), b.getCustomer().getFirstName());
            }
            return cmp;
        };

        Comparator<Sale> cmpByPrice = Comparator.comparingDouble(Sale::calculateGrandTotal);

        Comparator<Sale> cmpByStoreAndSalespersonName = (a, b) -> {
            int cmp = CharSequence.compare(a.getStore().getStoreCode(), b.getStore().getStoreCode());
            if (cmp == 0) {
                cmp = CharSequence.compare(a.getSalesperson().getLastName(), b.getSalesperson().getLastName());
                if (cmp == 0) {
                    cmp = CharSequence.compare(a.getSalesperson().getFirstName(), b.getSalesperson().getFirstName());
                }
            }
            return cmp;
        };

        SortedList<Sale> sl1 = new SortedList<>(cmpByCustomerName);
        sl1.batchAdd(sales);
        for (int i = 0; i < sl1.size(); i++) {
            System.out.println(sl1.get(i));
        }

        SortedList<Sale> sl2 = new SortedList<>(cmpByPrice);
        sl2.batchAdd(sales);
        for (int i = 0; i < sl2.size(); i++) {
            System.out.println(sl2.get(i));
        }

        SortedList<Sale> sl3 = new SortedList<>(cmpByStoreAndSalespersonName);
        sl3.batchAdd(sales);
        for (int i = 0; i < sl3.size(); i++) {
            System.out.println(sl3.get(i));
        }

        if (sl1.isEmpty() || sl2.isEmpty() || sl3.isEmpty()) {
            System.out.println("You fucked up");
        }
    }
}
