package com.mgg;

import com.mgg.entity.Item;
import com.mgg.entity.Person;
import com.mgg.entity.Sale;
import com.mgg.entity.Store;
import com.mgg.io.PrintReport;
import com.mgg.io.ReadFile;

import java.util.ArrayList;
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
        List<Store> stores = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        List<Sale> sales = new ArrayList<>();

        PrintReport.generateSalespersonSummaryReport(sales, persons);
        PrintReport.generateStoreSalesSummaryReport(sales, stores);
        PrintReport.generateSalesReceipts(sales);
    }
}
