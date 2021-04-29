package com.mgg;

import com.mgg.entity.Item;
import com.mgg.entity.Person;
import com.mgg.entity.Sale;
import com.mgg.entity.Store;
import com.mgg.io.PrintSortedLists;
import com.mgg.io.ReadFile;

import java.util.List;

/**
 * @author Dylan Diehl
 * @author Alex Busch
 *
 * Main class for running all of the classes that load the data and generate reports.
 */

public class FileSalesReport {

    public static void main(String[] args) {
        List<Person> persons = ReadFile.readPersonsCSV();
        List<Store> stores = ReadFile.readStoresCSV(persons);
        List<Item> items = ReadFile.readItemsCSV();
        List<Sale> sales = ReadFile.readSaleCSV(persons, stores, items);

        PrintSortedLists.printList(sales);

//        PrintReport.generateSalespersonSummaryReport(sales, persons);
//        PrintReport.generateStoreSalesSummaryReport(sales, stores);
//        PrintReport.generateSalesReceipts(sales);
    }
}
