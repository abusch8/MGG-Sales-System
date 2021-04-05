package com.mgg;

import java.sql.SQLException;
import java.util.List;

public class DatabaseSalesReport {

    public static void main(String[] args) throws SQLException {
        List<Person> persons = LoadData.getPersonsDatabase();
        List<Store> stores = LoadData.getStoresDadatabase(persons);

        List<Item> items = ReadFile.readItemsCSV();
        List<Sale> sales = ReadFile.readSaleCSV(persons, stores, items);

        PrintReport.generateSalespersonSummaryReport(sales, persons);
        PrintReport.generateStoreSalesSummaryReport(sales, stores);
        //PrintReport.generateSalesReceipts(sales);
    }
}
