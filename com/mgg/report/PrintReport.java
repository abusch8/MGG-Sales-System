package com.mgg.report;

import com.mgg.entity.Customer;
import com.mgg.entity.Employee;
import com.mgg.entity.Item;
import com.mgg.entity.Person;
import com.mgg.entity.Sale;
import com.mgg.entity.Store;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This class handles all of the responsibilities with printing out our receipts and sales summaries.
 */
public class PrintReport {

    public static void generateSalespersonSummaryReport(List<Sale> sales, List<Person> persons) {
        //We are using comparators to make the list sorted by last name, Store Summary does the exact same thing with another comparator below.
        Comparator<Employee> cmpByEmployeeLastName = Comparator.comparing(Person::getLastName);

        System.out.println("+-----------------------------------------------------+");
        System.out.println("| Salesperson Summary Report                          |");
        System.out.println("+-----------------------------------------------------+");
        System.out.println("Salesperson                    # Sales    Grand Total  ");

        double total = 0;
        double grandTotal = 0;
        int salesCountTotal = 0;

        List<Employee> salespersons = new ArrayList<>();

        for (Person person : persons) {
            if (person instanceof Employee) {
                Employee employee = (Employee) person;
                employee.setSales(sales);
                salespersons.add(employee);
            }
        }

        salespersons.sort(cmpByEmployeeLastName);

        for (Employee salesperson : salespersons) {
            for (Sale sale : salesperson.getSales()) {
                total += sale.calculateGrandTotal();
                grandTotal += sale.calculateGrandTotal();
                salesCountTotal++;
            }

            StringBuilder printString = new StringBuilder();
            printString.append(String.format("%s, %s", salesperson.getLastName(), salesperson.getFirstName()));
            printString.append(" ".repeat(Math.max(0, 31 - printString.length())));
            printString.append(String.format("%-11d$%10.2f", salesperson.getSales().size(), (double) Math.round(total * 100) / 100));
            System.out.println(printString);
            total = 0;
        }

        System.out.println("+-----------------------------------------------------+");
        System.out.printf("                               %-11d$%10.2f\n\n\n", salesCountTotal, (double) Math.round(grandTotal * 100) / 100);
    }

    public static void generateStoreSalesSummaryReport(List<Sale> sales, List<Store> stores) {

        Comparator<Store> cmpByManagerLastName = Comparator.comparing(a -> a.getManager().getLastName());

        System.out.println("+----------------------------------------------------------------+");
        System.out.println("| Store Sales Summary Report                                     |");
        System.out.println("+----------------------------------------------------------------+");
        System.out.println("Store      Manager                        # Sales    Grand Total  ");

        double total = 0;
        double grandTotal = 0;
        int salesCountTotal = 0;

        stores.sort(cmpByManagerLastName);

        for (Store store : stores) {
            store.setSales(sales);

            for (Sale sale : store.getSales()) {
                total += sale.calculateGrandTotal();
                grandTotal += sale.calculateGrandTotal();
                salesCountTotal++;
            }

            StringBuilder printString = new StringBuilder();
            printString.append(String.format("%s     %s, %s", store.getStoreCode(), store.getManager().getLastName(), store.getManager().getFirstName()));
            printString.append(" ".repeat(Math.max(0, 42 - printString.length())));
            printString.append(String.format("%-11d$%10.2f", store.getSales().size(), (double) Math.round(total * 100) / 100));
            System.out.println(printString);
            total = 0;
        }

        System.out.println("+----------------------------------------------------------------+");
        System.out.printf("                                          %-11d$%10.2f\n\n\n", salesCountTotal, (double) Math.round(grandTotal * 100) / 100);
    }

    public static void generateSalesReceipts(List<Sale> sales) {

        Comparator<Sale> cmpByCustomerLastName = Comparator.comparing(a -> a.getCustomer().getLastName());

        sales.sort(cmpByCustomerLastName);

        for (Sale sale : sales) {
            System.out.printf("Sale\t#%s\n", sale.getSaleCode());
            System.out.printf("Store\t#%s\n", sale.getStore().getStoreCode());
            System.out.printf("Customer:\n%s, %s (%s)\n", sale.getCustomer().getLastName(), sale.getCustomer().getFirstName(), sale.getCustomer().emailsToString());
            System.out.printf("\t%s\n\n", sale.getCustomer().getAddress().toString());
            System.out.printf("Sales Person:\n%s, %s, (%s) \n", sale.getSalesperson().getLastName(), sale.getSalesperson().getFirstName(), sale.getSalesperson().emailsToString());
            System.out.printf("\t%s\n\n", sale.getSalesperson().getAddress().toString());

            System.out.println("Item                                                               Total");
            System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-                          -=-=-=-=-=-");

            for (Item item : sale.getItems()) {
                System.out.println(item.receiptToString());
            }

            System.out.println("                                                             -=-=-=-=-=-");
            System.out.printf("                                                    Subtotal $%10.2f\n", sale.calculateSubTotal());
            System.out.printf("                                                         Tax $%10.2f\n", sale.calculateTax());
            if (!(sale.getCustomer() instanceof Customer)) {
                System.out.printf("                                           Discount (%5.2f%%) $%10.2f\n", sale.getCustomer().getDiscountAmount() * 100, sale.calculateDiscount());
            }
            System.out.printf("                                                 Grand Total $%10.2f\n\n\n", (double) Math.round(sale.calculateGrandTotal() * 100) / 100);
        }
    }
}