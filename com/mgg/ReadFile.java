package com.mgg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to read from 3 csv files and store their contents accordingly.
 */
public class ReadFile {

    /**
     * This method is used to read the "Persons.csv" file and store the contents accordingly.
     * @return List<Person>
     */
    public static List<Person> readPersonsCSV() {
        List<Person> persons = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/Persons.csv"));
            int count = Integer.parseInt(reader.readLine()); //convert first line from string->int(how many lines there are in the file)
            for (int i = 0; i < count; i++) {
                String[] content = reader.readLine().split(","); //use delimiter to separate contents
                Name name = new Name(content[2], content[3]);

                Address address = new Address(content[4], content[5], content[6], content[7], content[8]);

                List<String> emailList = new ArrayList<>(); //In-case someone has more than one email, we have created a list to store them.
                for (int j = 9; j <= content.length - 1; j++) {
                    emailList.add(content[j]); //filling ArrayList with emails
                }

                Person person = null;
                switch (content[1]) { //switch statement for properly identifying which type it is
                    case "E" -> person = new Employee(content[0], name, address, emailList);
                    case "C" -> person = new Customer(content[0], name, address, emailList);
                    case "P" -> person = new PlatinumMember(content[0], name, address, emailList);
                    case "G" -> person = new GoldMember(content[0], name, address, emailList);
                }
                persons.add(person); //add person to list
            }
            reader.close();
            return persons;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to read from the "Stores.csv" file and grab its contents.
     * @param persons This is the list of people from the previous method, or any list of "persons"
     * @return stores Returns the list of stores(its contents are stored in the list)
     */
    public static List<Store> readStoresCSV(List<Person> persons) {
        List<Store> stores = new ArrayList<>();
        try { BufferedReader reader = new BufferedReader(new FileReader("data/Stores.csv"));
            int count = Integer.parseInt(reader.readLine());
            for(int i = 0; i < count; i++) {
                String[] content = reader.readLine().split(",", -1);

                Employee manager = null;
                for(Person person : persons) {
                    if(person.getPersonId().equals(content[1])) {
                        manager = new Employee(person.getPersonId(), person.getName(), person.getAddress(), person.getEmails());
                    }
                }

                Address address = new Address(content[2], content[3], content[4], content[5], content[6]);
                Store store = new Store(content[0], manager, address);
                stores.add(store);
            }
            reader.close();
            return stores;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method reads the "Items.csv" file and stores its contents into a list of type <Item>.
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
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
