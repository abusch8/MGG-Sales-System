package com.mgg;

import java.util.List;

/**
 *
 * @author abusch
 * @author ddiehl
 *
 * Takes in 3 CSV files, each in specific format, and outputs/formats them into a JSON and XML file accordingly.
 *
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