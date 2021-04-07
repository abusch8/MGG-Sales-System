package com.mgg;

import java.util.List;
/**
 * This class uses all of the other classes to get our desired output.
 */
public class SalesReport {

    public static void main(String[] args) {
        List<Person> persons = LoadData.readPersonDatabase();
        List<Store> stores = LoadData.readStoreDatabase();
        List<Sale> sales = LoadData.readSaleDatabase();
        
        PrintReport.generateSalespersonSummaryReport(sales, persons);
        PrintReport.generateStoreSalesSummaryReport(sales, stores);
        PrintReport.generateSalesReceipts(sales);
    }
}