package com.mgg.sql;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Database interface class
 */
public class SalesData {

	private static final Logger LOGGER = LogManager.getLogger(LoadData.class);

	static {
		Configurator.initialize(new DefaultConfiguration());
		Configurator.setRootLevel(Level.INFO);
	}

	/**
	 * Removes all sales records from the database.
	 */
	public static void removeAllSales() {
		Connection conn = Database.connect();

		String query1 = "SET FOREIGN_KEY_CHECKS = 0;";
		String query2 = "truncate Sale;";
		String query3 = "SET FOREIGN_KEY_CHECKS = 1;";

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query1);
			LOGGER.info(ps);
			ps.executeUpdate();

			ps = conn.prepareStatement(query2);
			LOGGER.info(ps);
			ps.executeUpdate();

			ps = conn.prepareStatement(query3);
			LOGGER.info(ps);
			ps.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error(e);
			throw new RuntimeException(e);
		} finally {
			Database.disconnect(null, ps, conn);
		}
	}

	/**
	 * Removes the single sales record associated with the given
	 * <code>saleCode</code>
	 *
	 * @param saleCode
	 */
	public static void removeSale(String saleCode) {
		Connection conn = Database.connect();

		String query = "delete from Sale where saleCode = ?;";

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, saleCode);
			LOGGER.info(ps);
			ps.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error(e);
			throw new RuntimeException(e);
		} finally {
			Database.disconnect(null, ps, conn);
		}
	}

	/**
	 * Clears all tables of the database of all records.
	 */
	public static void clearDatabase() {
		Connection conn = Database.connect();

		String query1 = "SET FOREIGN_KEY_CHECKS = 0;";
		String query2 = "truncate SaleItem;";
		String query3 = "truncate Sale;";
		String query4 = "truncate Store;";
		String query5 = "truncate Email;";
		String query6 = "truncate Person;";
		String query7 = "truncate Item;";
		String query8 = "truncate Address;";
		String query9 = "SET FOREIGN_KEY_CHECKS = 1;";

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query1);
			LOGGER.info(ps);
			ps.executeUpdate();

			ps = conn.prepareStatement(query2);
			LOGGER.info(ps);
			ps.executeUpdate();

			ps = conn.prepareStatement(query3);
			LOGGER.info(ps);
			ps.executeUpdate();

			ps = conn.prepareStatement(query4);
			LOGGER.info(ps);
			ps.executeUpdate();

			ps = conn.prepareStatement(query5);
			LOGGER.info(ps);
			ps.executeUpdate();

			ps = conn.prepareStatement(query6);
			LOGGER.info(ps);
			ps.executeUpdate();

			ps = conn.prepareStatement(query7);
			LOGGER.info(ps);
			ps.executeUpdate();

			ps = conn.prepareStatement(query8);
			LOGGER.info(ps);
			ps.executeUpdate();

			ps = conn.prepareStatement(query9);
			LOGGER.info(ps);
			ps.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error(e);
			throw new RuntimeException(e);
		} finally {
			Database.disconnect(null, ps, conn);
		}

	}

	/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>type</code> will be one of "E", "G", "P" or "C" depending on the type
	 * (employee or type of customer).
	 *
	 * @param personCode
	 * @param type
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String type, String firstName, String lastName, String street,
			String city, String state, String zip, String country) {
		Connection conn = Database.connect();

		String query1 = "insert into Address(street, city, state, zip, country) values (?, ?, ?, ?, ?);";
		String query2 = "insert into Person(personCode, type, lastName, firstName, addressId) values (?, ?, ?, ?, ?);";
		String query3 = "select addressId from Address where street=? and city=? and state=? and zip=? and country=?;";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int zipCode = Integer.parseInt(zip);
			int addressId = 0;

			ps = conn.prepareStatement(query1);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setInt(4, zipCode);
			ps.setString(5, country);
			LOGGER.info(ps);
			ps.executeUpdate();

			ps = conn.prepareStatement(query3);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setInt(4, zipCode);
			ps.setString(5, country);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				addressId = rs.getInt("addressId");
			}

			ps = conn.prepareStatement(query2);
			ps.setString(1, personCode);
			ps.setString(2, type);
			ps.setString(3, lastName);
			ps.setString(4, firstName);
			ps.setInt(5, addressId);
			LOGGER.info(ps);
			ps.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error(e);
			throw new RuntimeException(e);
		} finally {
			Database.disconnect(rs, ps, conn);
		}
	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 *
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		Connection conn = Database.connect();

		String query1 = "select personId from Person where personCode = ?;";
		String query2 = "insert into Email(email, personId) values (?, ?);";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int personId = 0;
			ps = conn.prepareStatement(query1);
			ps.setString(1, personCode);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				personId = rs.getInt("personId");
			}

			ps = conn.prepareStatement(query2);
			ps.setString(1, email);
			ps.setInt(2, personId);
			LOGGER.info(ps);
			ps.executeQuery();

		} catch (SQLException e) {
			LOGGER.error(e);
			throw new RuntimeException(e);
		} finally {
			Database.disconnect(rs, ps, conn);
		}
	}

	/**
	 * Adds a store record to the database managed by the person identified by the
	 * given code.
	 *
	 * @param storeCode
	 * @param managerCode
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addStore(String storeCode, String managerCode, String street, String city, String state,
			String zip, String country) {
		Connection conn = Database.connect();

		String query1 = "select personId from Person where personCode = ?;";
		String query2 = "insert into Address(street, city, state, zip, country) values (?, ?, ?, ?, ?);";
		String query3 = "select addressId from Address where street = ? and city = ? and state = ? and zip = ? and country = ?;";
		String query4 = "insert into Store(storeCode, managerId, addressId) values (?, ?, ?);";

		int zipCode = Integer.parseInt(zip);

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int managerId = 0;
			int addressId = 0;

			ps = conn.prepareStatement(query1);
			ps.setString(1, managerCode);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				managerId = rs.getInt("personId");
			}

			ps = conn.prepareStatement(query2);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setInt(4, zipCode);
			ps.setString(5, country);
			LOGGER.info(ps);
			ps.executeUpdate();

			ps = conn.prepareStatement(query3);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setInt(4, zipCode);
			ps.setString(5, country);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				addressId = rs.getInt("addressId");
			}

			ps = conn.prepareStatement(query4);
			ps.setString(1, storeCode);
			ps.setInt(2, managerId);
			ps.setInt(3, addressId);
			LOGGER.info(ps);
			ps.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error(e);
			throw new RuntimeException(e);
		} finally {
			Database.disconnect(rs, ps, conn);
		}
	}

	/**
	 * Adds a sales item (product, service, subscription) record to the database
	 * with the given <code>name</code> and <code>basePrice</code>. The type of item
	 * is specified by the <code>type</code> which may be one of "PN", "PU", "PG",
	 * "SV", or "SB". These correspond to new products, used products, gift cards
	 * (for which <code>basePrice</code> will be <code>null</code>), services, and
	 * subscriptions.
	 *
	 * @param itemCode
	 * @param type
	 * @param name
	 * @param basePrice
	 */
	public static void addItem(String itemCode, String type, String name, Double basePrice) {
		Connection conn = Database.connect();

		String query = "insert into Item(itemCode, type, name, price) values (?, ?, ?, ?);";
		String query2 = "insert into Item(itemCode, type, name) values (?, ?, ?);";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			if (basePrice == null) {
				ps = conn.prepareStatement(query2);
				ps.setString(1, itemCode);
				ps.setString(2, type);
				ps.setString(3, name);
			} else {
				ps = conn.prepareStatement(query);
				ps.setString(1, itemCode);
				ps.setString(2, type);
				ps.setString(3, name);
				ps.setDouble(4, basePrice);
			}
			LOGGER.info(ps);
			ps.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error(e);
			throw new RuntimeException(e);
		} finally {
			Database.disconnect(rs, ps, conn);
		}
	}

	/**
	 * Adds a sales record to the database with the given data.
	 *
	 * @param saleCode
	 * @param storeCode
	 * @param customerCode
	 * @param salesPersonCode
	 */
	public static void addSale(String saleCode, String storeCode, String customerCode, String salesPersonCode) {
		Connection conn = Database.connect();

		String query1 = "select storeId from Store where storeCode = ?;";
		String query2 = "select PersonId from Person where personCode = ?;";
		String query3 = "insert into Sale(saleCode, storeId, customerId, salespersonId) values (?, ?, ?, ?);";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int storeId = 0;
			int customerId = 0;
			int salespersonId = 0;

			ps = conn.prepareStatement(query1);
			ps.setString(1, storeCode);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				storeId = rs.getInt("storeId");
			}

			ps = conn.prepareStatement(query2);
			ps.setString(1, customerCode);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				customerId = rs.getInt("personId");
			}

			ps = conn.prepareStatement(query2);
			ps.setString(1, salesPersonCode);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				salespersonId = rs.getInt("personId");
			}

			ps = conn.prepareStatement(query3);
			ps.setString(1, saleCode);
			ps.setInt(2, storeId);
			ps.setInt(3, customerId);
			ps.setInt(4, salespersonId);
			LOGGER.info(ps);
			ps.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error(e);
			throw new RuntimeException(e);
		} finally {
			Database.disconnect(rs, ps, conn);
		}
	}

	/**
	 * Adds a particular product (new or used, identified by <code>itemCode</code>)
	 * to a particular sale record (identified by <code>saleCode</code>) with the
	 * specified quantity.
	 *
	 * @param saleCode
	 * @param itemCode
	 * @param quantity
	 */
	public static void addProductToSale(String saleCode, String itemCode, int quantity) {
		Connection conn = Database.connect();

		String query1 = "select saleId from Sale where saleCode = ?;";
		String query2 = "select itemId from Item where itemCode = ?;";
		String query3 = "insert into SaleItem(saleId, saleCode, itemId, quantity) value (?, ?, ?, ?);";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int saleId = 0;
			int itemId = 0;

			ps = conn.prepareStatement(query1);
			ps.setString(1, saleCode);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				saleId = rs.getInt("saleId");
			}

			ps = conn.prepareStatement(query2);
			ps.setString(1, itemCode);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				itemId = rs.getInt("itemId");
			}

			ps = conn.prepareStatement(query3);
			ps.setInt(1, saleId);
			ps.setString(2, saleCode);
			ps.setInt(3, itemId);
			ps.setInt(4, quantity);
			LOGGER.info(ps);
			ps.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error(e);
			throw new RuntimeException(e);
		} finally {
			Database.disconnect(rs, ps, conn);
		}
	}

	/**
	 * Adds a particular gift card (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) in the specified
	 * amount.
	 *
	 * @param saleCode
	 * @param itemCode
	 * @param amount
	 */
	public static void addGiftCardToSale(String saleCode, String itemCode, double amount) {
		Connection conn = Database.connect();

		String query1 = "select saleId from Sale where saleCode = ?;";
		String query2 = "select itemId from Item where itemCode = ?;";
		String query3 = "insert into SaleItem(saleId, saleCode, itemId, quantity) value (?, ?, ?, ?);";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int saleId = 0;
			int itemId = 0;

			ps = conn.prepareStatement(query1);
			ps.setString(1, saleCode);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				saleId = rs.getInt("saleId");
			}

			ps = conn.prepareStatement(query2);
			ps.setString(1, itemCode);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				itemId = rs.getInt("itemId");
			}

			ps = conn.prepareStatement(query3);
			ps.setInt(1,saleId);
			ps.setString(2,saleCode);
			ps.setInt(3,itemId);
			ps.setDouble(4,amount);
			LOGGER.info(ps);
			ps.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error(e);
			throw new RuntimeException(e);
		} finally {
			Database.disconnect(rs, ps, conn);
		}
	}

	/**
	 * Adds a particular service (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) which
	 * will be performed by the given employee for the specified number of
	 * hours.
	 *
	 * @param saleCode
	 * @param itemCode
	 * @param employeeCode
	 * @param billedHours
	 */
	public static void addServiceToSale(String saleCode, String itemCode, String employeeCode, double billedHours) {
		Connection conn = Database.connect();

		String query1 = "select personId from Person where personCode = ?;";
		String query2 = "select saleId from Sale where saleCode = ?;";
		String query3 = "select itemId from Item where itemCode = ?;";
		String query4 = "insert into SaleItem(saleId, saleCode, itemId, employeeId, numberOfHours) values (?,?,?,?,?);";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int employeeId = 0;
			int saleId = 0;
			int itemId = 0;

			ps = conn.prepareStatement(query1);
			ps.setString(1, employeeCode);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				employeeId = rs.getInt("personId");
			}

			ps = conn.prepareStatement(query2);
			ps.setString(1, saleCode);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				saleId = rs.getInt("saleId");
			}

			ps = conn.prepareStatement(query3);
			ps.setString(1, itemCode);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				itemId = rs.getInt("itemId");
			}

			ps = conn.prepareStatement(query4);
			ps.setInt(1,saleId);
			ps.setString(2,saleCode);
			ps.setInt(3,itemId);
			ps.setInt(4,employeeId);
			ps.setDouble(5, billedHours);
			LOGGER.info(ps);
			ps.executeUpdate();

		} catch(SQLException e) {
			LOGGER.error(e);
			throw new RuntimeException(e);
		} finally {
			Database.disconnect(rs, ps, conn);
		}


	}

	/**
	 * Adds a particular subscription (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) which
	 * is effective from the <code>startDate</code> to the <code>endDate</code>
	 * inclusive of both dates.
	 *
	 * @param saleCode
	 * @param itemCode
	 * @param startDate
	 * @param endDate
	 */
	public static void addSubscriptionToSale(String saleCode, String itemCode, String startDate, String endDate) {
		Connection conn = Database.connect();

		String query1 = "select saleId from Sale where saleCode = ?;";
		String query2 = "select itemId from Item where itemCode = ?;";
		String query3 = "insert into SaleItem(saleId, saleCode, itemId, beginDate, endDate) value (?, ?, ?, ?);";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int saleId = 0;
			int itemId = 0;

			ps = conn.prepareStatement(query1);
			ps.setString(1, saleCode);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				saleId = rs.getInt("saleId");
			}

			ps = conn.prepareStatement(query2);
			ps.setString(1, itemCode);
			LOGGER.info(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				itemId = rs.getInt("itemId");
			}

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //ex: 2020-03-31
			Date sDate = format.parse(startDate);
			Date eDate = format.parse(endDate);
			ps = conn.prepareStatement(query3);
			ps.setInt(1, saleId);
			ps.setInt(2, itemId);
			//i have no clue if this works. oh well!
			ps.setDate(3, (java.sql.Date) sDate);
			ps.setDate(4, (java.sql.Date) eDate);
			LOGGER.info(ps);
			ps.executeQuery();

		} catch (SQLException | ParseException e) {
			LOGGER.error(e);
			throw new RuntimeException(e);
		} finally {
			Database.disconnect(rs, ps, conn);
		}
	}
}
