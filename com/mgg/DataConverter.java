package com.mgg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DataConverter {

    public static void main(String[] args) {
        generatePersons();
        generateStores();
        generateItems();
    }

    public static void generatePersons() {
        System.out.println("=======================");
        System.out.println("    CUSTOMER REPORT    ");
        System.out.println("=======================");
        List<Person> persons = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/Persons.csv"));

            int count = Integer.parseInt(reader.readLine()); //convert first line from string->int(how many lines there are in the file)

            for(int i = 0; i < count; i++) {
                String[] content = reader.readLine().split(",", -1); //use delimiter to separate contents

                Name name = new Name(content[2], content[3]);
                Address address = new Address(content[4], content[5], content[6], content[7], content[8]);

                List<String> emailList = new ArrayList<>();
                for(int j = 9; j <= content.length-1; j++) {
                    emailList.add(content[j]); //filling ArrayList with emails
                }

                Person person = new Person(content[0], content[1], name, address, emailList);
                persons.add(person);
                //To do: Generate XML and JSON files here instead of printing.
                System.out.println(person.toString()); //print customer
            }
            reader.close();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateStores() {
        System.out.println("=======================");
        System.out.println("     STORES REPORT     ");
        System.out.println("=======================");
        List<Store> stores = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/Stores.csv"));

            int count = Integer.parseInt(reader.readLine()); //convert first line from string->int(how many lines there are in the file)

            for(int i = 0; i < count; i++) {
                String[] content = reader.readLine().split(",", -1);
                Address address = new Address(content[2], content[3], content[4], content[5], content[6]);
                Store store = new Store(content[0], content[1], address);
                stores.add(store);
                //To do: Generate XML and JSON files here instead of printing.
                System.out.println(store.toString());
            }
            reader.close();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateItems() {
        System.out.println("=======================");
        System.out.println("      ITEMS REPORT     ");
        System.out.println("=======================");
        List<Item> items = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/Items.csv"));

            int count = Integer.parseInt(reader.readLine()); //convert first line from string->int(how many lines there are in the file)

            for(int i = 0; i < count; i++) {
                String[] content = reader.readLine().split(",", -1);
                Item item = new Item(content[0], content[1], content[2], Double.parseDouble(content[3]));
                items.add(item);
                //To do: Generate XML and JSON files here instead of printing.
                System.out.println(item.toString());
            }
            reader.close();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}