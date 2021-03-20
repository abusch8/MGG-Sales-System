package com.mgg;

import java.util.List;

/**
 * @author Dylan Diehl
 * @author Alex Busch
 */

/**
 * Main class for running all of the classes, combining them to get our desired result.
 */
public class DataConverter {

    public static void main(String[] args) {
        List<Person> persons = ReadFile.readPersonsCSV();
        List<Store> stores = ReadFile.readStoresCSV(persons);
        List<Item> items = ReadFile.readItemsCSV();

        WriteFile.convertPersonToXML(persons);
        WriteFile.convertStoreToXML(stores);
        WriteFile.convertItemToXML(items);

        WriteFile.convertPersonToJSON(persons);
        WriteFile.convertStoreToJSON(stores);
        WriteFile.convertItemToJSON(items);
    }
}