package com.mgg;

import java.util.List;

/**
 * @author Dylan Diehl
 * @author Alex Busch
 *
 *
 *
 * Uses all of the other classes(ReadFile,WriteFile, etc) and runs them to get our desired result.
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