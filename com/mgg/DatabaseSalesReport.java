package com.mgg;

import com.mgg.entity.Person;
import com.mgg.entity.Sale;
import com.mgg.entity.Store;
import com.mgg.report.PrintReport;
import com.mgg.sql.LoadData;

import java.util.List;

/**
 * @author Dylan Diehl
 * @author Alex Busch
 *
 * Main class for running all of the classes that load the data and generate reports.
 */
public class DatabaseSalesReport {

    public static void main(String[] args) {
        List<Person> persons = LoadData.readPersonDatabase();
        List<Store> stores = LoadData.readStoreDatabase();
        List<Sale> sales = LoadData.readSaleDatabase();

        PrintReport.generateSalespersonSummaryReport(sales, persons);
        PrintReport.generateStoreSalesSummaryReport(sales, stores);
        PrintReport.generateSalesReceipts(sales);
    }
}