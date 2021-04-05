package com.mgg;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoadData {

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static List<Person> getPersonsDatabase() throws SQLException {
        List<Person> persons = new ArrayList<>();
        Connection conn = connect();

        String query1 = "select * from Person;";
        String query2 = "select * from Address where addressId = ?;";
        String query3 = "select a.email from Email a join Person b on a.personId = b.personId where b.personId = ?;";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query1);
            ps.executeQuery();

            while (rs.next()) {
                String personCode = rs.getString("personCode");
                String lastName = rs.getString("lastName");
                String firstName = rs.getString("firstName");
                String type = rs.getString("type");
                int addressId = rs.getInt("addressId");
                int personId = rs.getInt("personId");

                ps = conn.prepareStatement(query2);
                ps.setInt(1, addressId);
                ps.executeQuery();

                String street = rs.getString("street");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String zip = rs.getString("zip");
                String country = rs.getString("country");

                Address address = new Address(street, city, state, zip, country);

                ps = conn.prepareStatement(query3);
                ps.setInt(1, personId);
                ps.executeQuery();

                List<String> emails = new ArrayList<>();
                while (rs.next()) {
                    emails.add(rs.getString("email"));
                }

                Person person = null;
                switch (type) {
                    case "P" -> person = new PlatinumMember(personCode, lastName, firstName, address, emails);
                    case "G" -> person = new GoldMember(personCode, lastName, firstName, address, emails);
                    case "E" -> person = new Employee(personCode, lastName, firstName, address, emails);
                    case "C" -> person = new Customer(personCode, lastName, firstName, address, emails);
                }
                persons.add(person);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        return persons;
    }

    public static List<Store> getStoresDadatabase(List<Person> persons) throws SQLException {
        List<Store> stores = new ArrayList<>();
        Connection conn = connect();

        String query1 = "select * from Store;";
        String query2 = "select * from Person where type = \"E\" and personId = ?;";
        String query3 = "select * from Address where addressId = ?;";
//        String query4 = "select * from Sale where storeId = ?;";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query1);
            ps.executeQuery();

            while (rs.next()) {
                String storeCode= rs.getString("storeCode");
                int managerId = rs.getInt("managerId");
                int addressId = rs.getInt("addressId");
//                int storeId = rs.getInt("storeId");

                ps = conn.prepareStatement(query2);
                ps.setInt(1, managerId);
                ps.executeQuery();

                String managerCode = rs.getString("personCode");

                Employee manager = null;
                for (Person p : persons) {
                    if (p.getPersonId().equals(managerCode)) {
                        manager = new Employee(p.getPersonId(), p.getLastName(), p.getFirstName(), p.getAddress(), p.getEmails());
                    }
                }

                ps = conn.prepareStatement(query3);
                ps.setInt(1, addressId);
                ps.executeQuery();

                String street = rs.getString("street");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String zip = rs.getString("zip");
                String country = rs.getString("country");

                Address address = new Address(street, city, state, zip, country);

//                ps = conn.prepareStatement(query4);
//                ps.setInt(1, storeId);
//                ps.executeQuery();

                Store store = new Store(storeCode, manager, address);
                stores.add(store);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        return stores;
    }

    public static List<Item> getItemsDadatabase() throws SQLException {
        List<Item> items = new ArrayList<>();
        Connection conn = connect();

        return items;
    }

    public static List<Sale> getSaleDatabase(List<Store> stores, List<Person> persons, List<Item> items) throws SQLException {
        List<Sale> sales = new ArrayList<>();
        Connection conn = connect();

        String query1 = "select * from Sale;";
        String query2 = "select * from Store where storeId = ?;";
        String query3 = "select * from Person where personId = ?;";
        String query4 = "select * from SaleItem where saleId = ?;";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query1);
            ps.executeQuery();

            while (rs.next()) {
                String saleCode = rs.getString("saleCode");
                int storeId = rs.getInt("storeId");
                int customerId = rs.getInt("customerId");
                int salespersonId = rs.getInt("salespersonId");
                int saleId = rs.getInt("saleId");

                ps = conn.prepareStatement(query2);
                ps.setInt(1, storeId);
                ps.executeQuery();

                String storeCode = rs.getString("storeCode");

                Store store = null;
                for (Store s : stores) {
                    if (s.getStoreCode().equals(storeCode)) {
                        store = new Store(s.getStoreCode(), s.getManager(), s.getAddress());
                    }
                }

                ps = conn.prepareStatement(query3);
                ps.setInt(1, customerId);
                ps.executeQuery();

                String customerCode = rs.getString("customerCode");

                Customer customer = null;
                for (Person p : persons) {
                    if (p.getPersonId().equals(customerCode)) {
                        customer = new Customer(p.getPersonId(), p.getLastName(), p.getFirstName(), p.getAddress(), p.getEmails());
                    }
                }

                ps = conn.prepareStatement(query3);
                ps.setInt(1, salespersonId);
                ps.executeQuery();

                String salespersonCode = rs.getString("salespersonCode");

                Employee salesperson = null;
                for (Person c : persons) {
                    if (c.getPersonId().equals(salespersonCode)) {
                        salesperson = new Employee(c.getPersonId(), c.getLastName(), c.getFirstName(), c.getAddress(), c.getEmails());
                    }
                }



                Sale sale = new Sale(saleCode, store, customer, salesperson, items);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        return sales;
    }
}
