package com.mgg.io;

import com.mgg.entity.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to read from 3 csv files and store their contents accordingly.
 */
public class ReadFile {

    /**
     * This method is used to read the "Persons.csv" file and store the contents accordingly.
     *
     * @return List<Person> A list of people gathered from the .csv
     */
    public static List<Person> readPersonsCSV() {
        List<Person> persons = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/Persons.csv"));
            int count = Integer.parseInt(reader.readLine());
            for (int i = 0; i < count; i++) {
                String[] content = reader.readLine().split(",");
                Address address = new Address(content[4], content[5], content[6], content[7], content[8]);
                List<String> emailList = new ArrayList<>();
                for (int j = 9; j <= content.length - 1; j++) {
                    emailList.add(content[j]);
                }
                Person person = null;
                switch (content[1]) {
                    case "P" -> person = new PlatinumMember(content[0], content[2], content[3], address, emailList);
                    case "G" -> person = new GoldMember(content[0], content[2], content[3], address, emailList);
                    case "E" -> person = new Employee(content[0], content[2], content[3], address, emailList);
                    case "C" -> person = new Customer(content[0], content[2], content[3], address, emailList);
                }
                persons.add(person);
            }
            reader.close();
            return persons;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to read from the "Stores.csv" file and grab its contents.
     *
     * @param persons This is the list of people from the previous method, or any list of "persons"
     * @return stores Returns the list of stores(its contents are stored in the list)
     */
    public static List<Store> readStoresCSV(List<Person> persons) {
        List<Store> stores = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/Stores.csv"));
            int count = Integer.parseInt(reader.readLine());
            for (int i = 0; i < count; i++) {
                String[] content = reader.readLine().split(",", -1);
                Employee manager = null;
                for (Person person : persons) {
                    if (person.getPersonId().equals(content[1])) {
                        manager = new Employee(person.getPersonId(), person.getLastName(), person.getFirstName(), person.getAddress(), person.getEmails());
                    }
                }
                Address address = new Address(content[2], content[3], content[4], content[5], content[6]);
                Store store = new Store(content[0], manager, address);
                stores.add(store);
            }
            reader.close();
            return stores;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method reads the "Items.csv" file and stores its contents into a list of type <Item>.
     *
     * @return items The list of items with their respective contents.
     */
    public static List<Item> readItemsCSV() {
        List<Item> items = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/Items.csv"));
            int count = Integer.parseInt(reader.readLine());
            for (int i = 0; i < count; i++) {
                String[] content = reader.readLine().split(",");
                Item item = null;
                switch (content[1]) {
                    case "PN" -> item = new NewProduct(content[0], content[2], Double.parseDouble(content[3]));
                    case "PU" -> item = new UsedProduct(content[0], content[2], Double.parseDouble(content[3]));
                    case "PG" -> item = new GiftCard(content[0], content[2]);
                    case "SV" -> item = new Service(content[0], content[2], Double.parseDouble(content[3]));
                    case "SB" -> item = new Subscription(content[0], content[2], Double.parseDouble(content[3]));
                }
                items.add(item);
            }
            reader.close();
            return items;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method reads the "Sales.csv" file and stores its contents into a list of type <Sale>
     *
     * @return sales the list of sales with their respective content from the csv.
     */
    public static List<Sale> readSaleCSV(List<Person> personList, List<Store> storeList, List<Item> itemList) {
        List<Sale> sales = new ArrayList<>();
        Store store = null;
        Person customer = null;
        Employee salesperson = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/Sales.csv"));
            int count = Integer.parseInt(reader.readLine());
            for (int i = 0; i < count; i++) {
                String[] content = reader.readLine().split(",");
                List<Item> items = new ArrayList<>();
                Item item;
                currentLine:
                {
                    for (int j = 4; j < content.length; j++) {
                        int k = 0;
                        while (k < itemList.size()) {
                            if (content[j].equals(itemList.get(k).getCode())) {
                                Item currentExistingItem = itemList.get(k);
                                switch (currentExistingItem.getClass().getSimpleName()) {
                                    case "NewProduct" -> {
                                        item = new NewProduct((NewProduct) currentExistingItem, Integer.parseInt(content[j + 1]));
                                        items.add(item);
                                        k = 0;
                                        if (j + 2 >= content.length) {
                                            break currentLine;
                                        } else {
                                            j += 2;
                                        }
                                    } case "UsedProduct" -> {
                                        item = new UsedProduct((UsedProduct) currentExistingItem, Integer.parseInt(content[j + 1]));
                                        items.add(item);
                                        k = 0;
                                        if (j + 2 >= content.length) {
                                            break currentLine;
                                        } else {
                                            j += 2;
                                        }
                                    } case "GiftCard" -> {
                                        item = new GiftCard((GiftCard) currentExistingItem, Double.parseDouble(content[j + 1]));
                                        items.add(item);
                                        k = 0;
                                        if (j + 2 >= content.length) {
                                            break currentLine;
                                        } else {
                                            j += 2;
                                        }
                                    } case "Service" -> {
                                        Employee employee = null;
                                        for (Person existingPerson : personList) {
                                            if (content[j + 1].equals(existingPerson.getPersonId())) {
                                                employee = new Employee(existingPerson.getPersonId(), existingPerson.getLastName(), existingPerson.getFirstName(), existingPerson.getAddress(), existingPerson.getEmails());
                                            }
                                        }
                                        item = new Service((Service) currentExistingItem, employee, Double.parseDouble(content[j + 2]));
                                        items.add(item);
                                        k = 0;
                                        if (j + 3 >= content.length) {
                                            break currentLine;
                                        } else {
                                            j += 3;
                                        }
                                    } case "Subscription" -> {
                                        item = new Subscription((Subscription) currentExistingItem, LocalDate.parse(content[j + 1]), LocalDate.parse(content[j + 2]));
                                        items.add(item);
                                        k = 0;
                                        if (j + 3 >= content.length) {
                                            break currentLine;
                                        } else {
                                            j += 3;
                                        }
                                    }
                                }
                            } else {
                                k++;
                            }
                            for (Store existingStore : storeList) {
                                if (content[1].equals(existingStore.getStoreCode())) {
                                    store = new Store(existingStore.getStoreCode(), existingStore.getManager(), existingStore.getAddress());
                                }
                            }
                            for (Person existingPerson : personList) {
                                if (content[2].equals(existingPerson.getPersonId())) {
                                    switch (existingPerson.getClass().getSimpleName()) {
                                        case "PlatinumMember" -> customer = new PlatinumMember(existingPerson.getPersonId(), existingPerson.getLastName(), existingPerson.getFirstName(), existingPerson.getAddress(), existingPerson.getEmails());
                                        case "GoldMember" -> customer = new GoldMember(existingPerson.getPersonId(), existingPerson.getLastName(), existingPerson.getFirstName(), existingPerson.getAddress(), existingPerson.getEmails());
                                        case "Employee" -> customer = new Employee(existingPerson.getPersonId(), existingPerson.getLastName(), existingPerson.getFirstName(), existingPerson.getAddress(), existingPerson.getEmails());
                                        case "Customer" -> customer = new Customer(existingPerson.getPersonId(), existingPerson.getLastName(), existingPerson.getFirstName(), existingPerson.getAddress(), existingPerson.getEmails());
                                    }
                                }
                                if (content[3].equals(existingPerson.getPersonId())) {
                                    salesperson = new Employee(existingPerson.getPersonId(), existingPerson.getLastName(), existingPerson.getFirstName(), existingPerson.getAddress(), existingPerson.getEmails());
                                }
                            }
                        }
                    }
                }
                Sale sale = new Sale(content[0], store, customer, salesperson, items);
                sales.add(sale);
            }
            reader.close();
            return sales;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}