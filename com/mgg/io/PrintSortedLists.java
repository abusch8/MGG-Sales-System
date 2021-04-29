package com.mgg.io;

import com.mgg.entity.Sale;
import com.mgg.util.SortedList;

import java.util.Comparator;
import java.util.List;

public class PrintSortedLists {

    public static void printList(List<Sale> sales) {

        Comparator<Sale> cmpByCustomer = (a, b) -> {
            int cmp = CharSequence.compare(a.getCustomer().getLastName(), b.getCustomer().getLastName());
            if (cmp == 0) {
                cmp = CharSequence.compare(a.getCustomer().getFirstName(), b.getCustomer().getFirstName());
            }
            return cmp;
        };

        Comparator<Sale> cmpByTotal = (a,b) -> Double.compare(b.calculateGrandTotal(), a.calculateGrandTotal());

        Comparator<Sale> cmpByStore = (a, b) -> {
            int cmp = CharSequence.compare(a.getStore().getStoreCode(), b.getStore().getStoreCode());
            if (cmp == 0) {
                cmp = CharSequence.compare(a.getSalesperson().getLastName(), b.getSalesperson().getLastName());
                if (cmp == 0) {
                    cmp = CharSequence.compare(a.getSalesperson().getFirstName(), b.getSalesperson().getFirstName());
                }
            }
            return cmp;
        };

        SortedList<Sale> sl1 = new SortedList<>(cmpByCustomer);
        sl1.batchAdd(sales);

        System.out.println("+-------------------------------------------------------------------------+");
        System.out.println("| Sales by Customer                                                       |");
        System.out.println("+-------------------------------------------------------------------------+");
        System.out.println("Sale       Store      Customer             Salesperson          Total");
        for (int i = 0; i < sl1.size(); i++) {
            System.out.println(sl1.get(i).toString());
        }
        System.out.print("\n\n");

        SortedList<Sale> sl2 = new SortedList<>(cmpByTotal);
        sl2.batchAdd(sales);

        System.out.println("+-------------------------------------------------------------------------+");
        System.out.println("| Sales by Total                                                          |");
        System.out.println("+-------------------------------------------------------------------------+");
        System.out.println("Sale       Store      Customer             Salesperson          Total");
        for (int i = 0; i < sl2.size(); i++) {
            System.out.println(sl2.get(i).toString());
        }
        System.out.print("\n\n");

        SortedList<Sale> sl3 = new SortedList<>(cmpByStore);
        sl3.batchAdd(sales);

        System.out.println("+-------------------------------------------------------------------------+");
        System.out.println("| Sales by Store                                                          |");
        System.out.println("+-------------------------------------------------------------------------+");
        System.out.println("Sale       Store      Customer             Salesperson          Total");
        for (int i = 0; i < sl3.size(); i++) {
            System.out.println(sl3.get(i).toString());
        }
        System.out.print("\n\n");
    }
}
