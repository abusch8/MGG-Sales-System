package com.mgg;

import com.mgg.entity.Sale;
import com.mgg.io.PrintSortedLists;
import com.mgg.sql.LoadData;

import java.util.List;

public class SalesReport {

    public static void main(String[] args) {
        List<Sale> sales = LoadData.readSaleDatabase();
        PrintSortedLists.printList(sales);
    }
}
