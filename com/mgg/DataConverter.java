package com.mgg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.ClassAliasingMapper;
import com.google.gson.*;

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
        generatePersons();
        generateStores();
        generateItems();
    }

    public static void generatePersons() {
        //documentation is only for the first function, as the following (2) functions are very similar in structure and content, and would be redundant if included in all.
        List<Person> persons = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/Persons.csv"));
            int count = Integer.parseInt(reader.readLine()); //convert first line from string->int(how many lines there are in the file)
            for(int i = 0; i < count; i++) {
                String[] content = reader.readLine().split(",", -1); //use delimiter to separate contents
                Name name = new Name(content[2], content[3]);
                Address address = new Address(content[4], content[5], content[6], content[7], content[8]);

                List<String> emailList = new ArrayList<>(); //In-case someone has more than one email, we have created a list to store them.
                for(int j = 9; j <= content.length-1; j++) {
                    emailList.add(content[j]); //filling ArrayList with emails
                }
                Person person = new Person(content[0], content[1], name, address, emailList);
                persons.add(person);
            }
            reader.close(); //close the reader and begin writing files
            XStream xstream = new XStream(new DomDriver());
            xstream.alias("people", List.class); //Change the 'list' class to display "people"
            xstream.alias("person", Person.class); //Change the 'person' class to display "person"

            ClassAliasingMapper mapper = new ClassAliasingMapper(xstream.getMapper());
            mapper.addClassAlias("email", String.class); //searches for the string class and changes the display to "email"
            xstream.registerLocalConverter(Person.class, "emails", new CollectionConverter(mapper));

            BufferedWriter xml = new BufferedWriter(new FileWriter("data/Persons.xml")); //writing to the file for XML
            String xml_output = xstream.toXML(persons);
            xml.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.write(xml_output);
            xml.close(); //close file for writing

            BufferedWriter json = new BufferedWriter(new FileWriter("data/Persons.json")); //writing to the file for JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create(); //format so it is readable
            String json_output = gson.toJson(persons);
            json.write(json_output);
            json.close(); //close file for writing

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateStores() {
        List<Store> stores = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/Stores.csv"));

            int count = Integer.parseInt(reader.readLine());
            for(int i = 0; i < count; i++) {
                String[] content = reader.readLine().split(",", -1);
                Address address = new Address(content[2], content[3], content[4], content[5], content[6]);
                Store store = new Store(content[0], content[1], address);
                stores.add(store);
            }
            reader.close();
            XStream xstream = new XStream(new DomDriver());
            xstream.alias("stores", List.class);
            xstream.alias("store", Store.class);

            BufferedWriter writer = new BufferedWriter(new FileWriter("data/Stores.xml"));
            String xml_output = xstream.toXML(stores);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write(xml_output);
            writer.close();

            BufferedWriter json = new BufferedWriter(new FileWriter("data/Stores.json"));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json_output = gson.toJson(stores);
            json.write(json_output);
            json.close();

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateItems() {
        List<Item> items = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/Items.csv"));

            int count = Integer.parseInt(reader.readLine());
            for(int i = 0; i < count; i++) {
                String[] content = reader.readLine().split(",", -1);
                Item item = new Item(content[0], content[1], content[2], Double.parseDouble(content[3]));
                items.add(item);
            }
            reader.close();
            XStream xstream = new XStream(new DomDriver());
            xstream.alias("items", List.class);
            xstream.alias("item", Item.class);

            BufferedWriter writer = new BufferedWriter(new FileWriter("data/Items.xml"));
            String xml_output = xstream.toXML(items);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write(xml_output);
            writer.close();

            BufferedWriter json = new BufferedWriter(new FileWriter("data/Items.json"));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json_output = gson.toJson(items);
            json.write(json_output);
            json.close();

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}