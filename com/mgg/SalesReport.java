package com.mgg;

import java.util.List;

/**
 * @author Dylan Diehl
 * @author Alex Busch
 */

/**
 * Main class for running all of the classes that load the data and generate reports.
 */
public class SalesReport {

    public static void main(String[] args) {
        List<Person> persons = LoadData.readPersonDatabase();
        List<Store> stores = LoadData.readStoreDatabase();
        List<Sale> sales = LoadData.readSaleDatabase();
//
//        List<Person> persons = ReadFile.readPersonsCSV();
//        List<Store> stores = ReadFile.readStoresCSV(persons);
//        List<Item> items = ReadFile.readItemsCSV();
//        List<Sale> sales = ReadFile.readSaleCSV(persons, stores, items);
        
        PrintReport.generateSalespersonSummaryReport(sales, persons);
        PrintReport.generateStoreSalesSummaryReport(sales, stores);
        PrintReport.generateSalesReceipts(sales);
    }
}