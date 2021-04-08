package com.mgg;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that loads the data from an SQL database into their appropriate classes.
 */
public class LoadData {

    private static final Logger LOGGER = LogManager.getLogger(LoadData.class);

    static {
        Configurator.initialize(new DefaultConfiguration());
        Configurator.setRootLevel(Level.INFO);
    }

    /**
     * This method connects to the database, the userinfo can be modified in the DatabaseInfo class
     * @return conn - the connection to the database.
     */
    private static Connection connect() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
        } catch (SQLException e) {
            LOGGER.error(String.format("Failed to connect to database @ %s", DatabaseInfo.URL));
            throw new RuntimeException(e);
        }
        return conn;
    }

    /**
     * Disconnects us from the database.
     * @param rs Resultset
     * @param ps preparedStatement
     * @param conn connection
     */
    private static void disconnect(ResultSet rs, PreparedStatement ps, Connection conn) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This method retrieves an address and returns it.
     * @param addressId numeric ID that leads to an address.
     * @return address - an address
     */
    private static Address retrieveAddress(int addressId) {
        Address address = null;
        Connection conn = connect();

        String query = "select * from Address where addressId = ?;";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, addressId);
            LOGGER.info(String.format("Executing query: %s", ps));
            rs = ps.executeQuery();

            if (rs.next()) {
                String street = rs.getString("street");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String zip = String.valueOf(rs.getInt("zip"));
                String country = rs.getString("country");

                address = new Address(street, city, state, zip, country);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            disconnect(rs, ps, conn);
        }
        return address;
    }

    /**
     * This method retrieves a person, and returns it.
     * @param personId numeric ID that leads to a person
     * @return person - a person
     */
    private static Person retrievePerson(int personId) {
        Person person = null;
        Connection conn = connect();

        String query1 = "select * from Person where personId = ?";
        String query2 = "select a.email from Email a join Person b on a.personId = b.personId where b.personId = ?;";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query1);
            ps.setInt(1, personId);
            LOGGER.info(String.format("Executing query: %s", ps));
            rs = ps.executeQuery();

            if (rs.next()) {
                String personCode = rs.getString("personCode");
                String lastName = rs.getString("lastName");
                String firstName = rs.getString("firstName");
                String type = rs.getString("type");
                int addressId = rs.getInt("addressId");

                Address address = retrieveAddress(addressId);

                ps = conn.prepareStatement(query2);
                ps.setInt(1, personId);
                LOGGER.info(String.format("Executing query: %s", ps));
                rs = ps.executeQuery();

                List<String> emails = new ArrayList<>();
                while (rs.next()) {
                    emails.add(rs.getString("email"));
                }

                switch (type) {
                    case "P" -> person = new PlatinumMember(personCode, lastName, firstName, address, emails);
                    case "G" -> person = new GoldMember(personCode, lastName, firstName, address, emails);
                    case "E" -> person = new Employee(personCode, lastName, firstName, address, emails);
                    case "C" -> person = new Customer(personCode, lastName, firstName, address, emails);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            disconnect(rs, ps, conn);
        }
        return person;
    }

    /**
     * This method retrieves a person, and returns it.
     * @param storeId an ID that leads to a store.
     * @return store - a store
     */
    private static Store retrieveStore(int storeId) {
        Store store = null;
        Connection conn = connect();

        String query = "select * from Store where storeId = ?;";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, storeId);
            LOGGER.info(String.format("Executing query: %s", ps));
            rs = ps.executeQuery();

            if (rs.next()) {
                String storeCode= rs.getString("storeCode");
                Employee manager = (Employee) retrievePerson(rs.getInt("managerId"));
                Address address = retrieveAddress(rs.getInt("addressId"));

                store = new Store(storeCode, manager, address);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            disconnect(rs, ps, conn);
        }
        return store;
    }
    /**
     * This method retrieves an item, and returns it.
     * @param itemId an ID that leads to a store.
     * @return item - an item
     */
    private static Item retrieveItem(int itemId) {
        Item item = null;
        Connection conn = connect();

        String query = "select * from Item where itemId = ?;";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, itemId);
            LOGGER.info(String.format("Executing query: %s", ps));
            rs = ps.executeQuery();

            if (rs.next()) {
                String code = rs.getString("itemCode");
                String type = rs.getString("type");
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                switch (type) {
                    case "PN" -> item = new NewProduct(code, name, price);
                    case "PU" -> item = new UsedProduct(code, name, price);
                    case "PG" -> item = new GiftCard(code, name);
                    case "SV" -> item = new Service(code, name, price);
                    case "SB" -> item = new Subscription(code, name, price);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            disconnect(rs, ps, conn);
        }
        return item;
    }
    /**
     * This method retrieves a sale, and returns it.
     * @param saleId ID that leads to a sale.
     * @return sale - a sale
     */
    private static Sale retrieveSale(int saleId) {
        Sale sale = null;
        Connection conn = connect();

        String query1 = "select * from Sale where saleId = ?;";
        String query2 = "select a.*, b.quantity, b.employeeId, b.numberOfHours, b.beginDate, b.endDate " +
                        "from Item a join SaleItem b on a.itemId = b.itemId where saleId = ?;";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query1);
            ps.setInt(1, saleId);
            LOGGER.info(String.format("Executing query: %s", ps));
            rs = ps.executeQuery();

            if (rs.next()) {
                String saleCode = rs.getString("saleCode");
                Store store = retrieveStore(rs.getInt("storeId"));
                Person customer = retrievePerson(rs.getInt("customerId"));
                Employee salesperson = (Employee) retrievePerson(rs.getInt("salespersonId"));

                ps = conn.prepareStatement(query2);
                ps.setInt(1, saleId);
                LOGGER.info(String.format("Executing query: %s", ps));
                rs = ps.executeQuery();

                List<Item> items = new ArrayList<>();
                while (rs.next()) {
                    Item item = retrieveItem(rs.getInt("itemId"));
                    switch (item.getClass().getSimpleName()) {
                        case "NewProduct" -> {
                            ((NewProduct) item).setQuantity(rs.getInt("quantity"));
                        } case "UsedProduct" -> {
                            ((UsedProduct) item).setQuantity(rs.getInt("quantity"));
                        } case "GiftCard" -> {
                            ((GiftCard) item).setBasePrice(rs.getDouble("quantity"));
                        } case "Service" -> {
                            ((Service) item).setEmployee((Employee) retrievePerson(rs.getInt("employeeId")));
                            ((Service) item).setNumHours(rs.getInt("numberOfHours"));
                        } case "Subscription" -> {
                            LocalDate beginDate = Instant.ofEpochMilli(rs.getDate("beginDate").getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                            ((Subscription) item).setBeginDate(beginDate);
                            LocalDate endDate = Instant.ofEpochMilli(rs.getDate("endDate").getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                            ((Subscription) item).setEndDate(endDate);
                        }
                    }
                items.add(item);
                }
            sale = new Sale(saleCode, store, customer, salesperson, items);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            disconnect(rs, ps, conn);
        }
        return sale;
    }

    /**
     * Reads the data from the SQL database and returns a list of people
     * @return persons - a list of people
     */
    public static List<Person> readPersonDatabase() {
        List<Person> persons = new ArrayList<>();
        Connection conn = connect();

        String query = "select personId, type from Person;";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            LOGGER.info(String.format("Executing query: %s", ps));
            rs = ps.executeQuery();

            while (rs.next()) {
                persons.add(retrievePerson(rs.getInt("personId")));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            disconnect(rs, ps, conn);
        }
        return persons;
    }

    /**
     * Reads the data from the SQL database and returns a list of stores
     * @return stores - a list of stores
     */
    public static List<Store> readStoreDatabase() {
        List<Store> stores = new ArrayList<>();
        Connection conn = connect();

        String query = "select storeId from Store;";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            LOGGER.info(String.format("Executing query: %s", ps));
            rs = ps.executeQuery();

            while (rs.next()) {
                stores.add(retrieveStore(rs.getInt("storeId")));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            disconnect(rs, ps, conn);
        }
        return stores;
    }

    /**
     * Reads the data from the SQL database and returns a list of items
     * @return items - a list of items
     */
    public static List<Item> readItemDatabase() {
        List<Item> items = new ArrayList<>();
        Connection conn = connect();

        String query = "select itemId from Item;";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            LOGGER.info(String.format("Executing query: %s", ps));
            rs = ps.executeQuery();

            while (rs.next()) {
                items.add(retrieveItem(rs.getInt("itemId")));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            disconnect(rs, ps, conn);
        }
        return items;
    }
    /**
     * Reads the data from the SQL database and returns a list of sales
     * @return sales - a list of sales
     */
    public static List<Sale> readSaleDatabase() {
        List<Sale> sales = new ArrayList<>();
        Connection conn = connect();

        String query = "select saleId from Sale;";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            LOGGER.info(String.format("Executing query: %s", ps));
            rs = ps.executeQuery();

            while (rs.next()) {
                sales.add(retrieveSale(rs.getInt("saleId")));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            disconnect(rs, ps, conn);
        }
        return sales;
    }
}
