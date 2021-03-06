package com.mgg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.ClassAliasingMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class WriteFile {

    public static void convertPersonToXML(List<Person> persons) {
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("Persons", List.class); //Change the 'list' class to display "people"
        xstream.alias("Employee", Employee.class);
        xstream.alias("Customer", Customer.class);
        xstream.alias("GoldMember", GoldMember.class);
        xstream.alias("PlatinumMember", PlatinumMember.class);

        ClassAliasingMapper mapper = new ClassAliasingMapper(xstream.getMapper());
        mapper.addClassAlias("email", String.class); //searches for the string class and changes the display to "email"
        xstream.registerLocalConverter(Person.class, "emails", new CollectionConverter(mapper));

        try {
            BufferedWriter xml = new BufferedWriter(new FileWriter("data/Persons.xml")); //writing to the file for XML
            String xml_output = xstream.toXML(persons);
            xml.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.write(xml_output);
            xml.close(); //close file for writing
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void convertStoreToXML(List<Store> stores) {
        try {
            XStream xstream = new XStream(new DomDriver());
            xstream.alias("stores", List.class);
            xstream.alias("store", Store.class);

            BufferedWriter writer = new BufferedWriter(new FileWriter("data/Stores.xml"));
            String xml_output = xstream.toXML(stores);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write(xml_output);
            writer.close();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void convertItemToXML(List<Item> items) {
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("items", List.class);
        xstream.alias("NewProduct", NewProduct.class);
        xstream.alias("UsedProduct", UsedProduct.class);
        xstream.alias("GiftCard", GiftCard.class);
        xstream.alias("Service", Service.class);
        xstream.alias("Subscription", Subscription.class);
        xstream.aliasField("itemCode", Item.class, "code");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data/Items.xml"));
            String xml_output = xstream.toXML(items);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write(xml_output);
            writer.close();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void convertPersonToJSON(List<Person> persons) {
        try {
            BufferedWriter json = new BufferedWriter(new FileWriter("data/Persons.json")); //writing to the file for JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create(); //format so it is readable
            String json_output = gson.toJson(persons);
            json.write(json_output);
            json.close(); //close file for writing
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void convertStoreToJSON(List<Store> stores) {
        try {
            BufferedWriter json = new BufferedWriter(new FileWriter("data/Stores.json"));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json_output = gson.toJson(stores);
            json.write(json_output);
            json.close();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void convertItemToJSON(List<Item> items) {
        try {
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