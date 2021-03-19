package com.mgg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PrintReport {

    public static void generateSalespersonSummaryReport(List<Sale> sales, List<Person> persons) {

        Comparator<Sale> cmpBySalesPerson = Comparator.comparing(a -> a.getSalesperson().getLastName());
        Comparator<Employee> cmpByEmployee = Comparator.comparing(Person::getLastName);

        System.out.println("+-----------------------------------------------------+");
        System.out.println("| Salesperson Summary Report                          |");
        System.out.println("+-----------------------------------------------------+");
        System.out.println("Salesperson                    # Sales    Grand Total  ");

        sales.sort(cmpBySalesPerson);

        int salesCount = 1;
        int salesCountTotal = 0;
        double total = 0;
        double grandTotal = 0;

        for (int i = 0; i < sales.size(); i++) {
            total += sales.get(i).calculateGrandTotal();
            grandTotal += sales.get(i).calculateGrandTotal();
            salesCountTotal++;
            if (i + 1 < sales.size() && sales.get(i).getSalesperson().getLastName().equals(sales.get(i + 1).getSalesperson().getLastName())) {
                salesCount++;
            } else {
                StringBuilder printString = new StringBuilder();
                printString.append(String.format("%s, %s", sales.get(i).getSalesperson().getLastName(), sales.get(i).getSalesperson().getFirstName()));
                int length = printString.length();
                for (int j = 0; j < 31 - length; j++) {
                    printString.append(" ");
                }
                printString.append(String.format("%-11d$%10.2f", salesCount, (double) Math.round(total * 100) / 100));
                System.out.println(printString);
                //grandTotal += total;
                total = 0;
                salesCount = 1;
            }
        }

        List<Employee> salespersons = new ArrayList<>();
        for (Person person : persons) {
            if (person instanceof Employee) {
                salespersons.add((Employee) person);
            }
        }

        salespersons.sort(cmpByEmployee);

        int counter;
        for (Employee salesperson : salespersons) {
            counter = 0;
            for (Sale sale : sales) {
                if (salesperson.getPersonId().equals(sale.getSalesperson().getPersonId())) {
                    counter++;
                }
            }
            if (counter == 0) {
                StringBuilder printString = new StringBuilder();
                printString.append(String.format("%s, %s", salesperson.getLastName(), salesperson.getFirstName()));
                int length = printString.length();
                for (int j = 0; j < 31 - length; j++) {
                    printString.append(" ");
                }
                printString.append(String.format("%-11d$%10.2f", 0, 0.00));
                System.out.println(printString);
            }
        }

        System.out.println("+-----------------------------------------------------+");
        System.out.printf("                               %-11d$%10.2f\n\n\n", salesCountTotal, (double) Math.round(grandTotal * 100) / 100);
    }

    public static void generateStoreSalesSummaryReport(List<Sale> sales, List<Store> stores) {

        Comparator<Sale> cmpByStoreCode = Comparator.comparing(a -> a.getStore().getStoreCode());
        Comparator<Store> cmpByStoreId = Comparator.comparing(Store::getStoreCode);

        System.out.println("+----------------------------------------------------------------+");
        System.out.println("| Store Sales Summary Report                                     |");
        System.out.println("+----------------------------------------------------------------+");
        System.out.println("Store      Manager                        # Sales    Grand Total  ");

        sales.sort(cmpByStoreCode);

        int salesCount = 1;
        int salesCountTotal = 0;
        double total = 0;
        double grandTotal = 0;

        for (int i = 0; i < sales.size(); i++) {
            total += sales.get(i).calculateGrandTotal();
            grandTotal += sales.get(i).calculateGrandTotal();
            salesCountTotal++;
            if (i + 1 < sales.size() && sales.get(i).getStore().getStoreCode().equals(sales.get(i + 1).getStore().getStoreCode())) {
                salesCount++;
            } else {
                StringBuilder printString = new StringBuilder();
                printString.append(String.format("%s %s, %s", sales.get(i).getStore().getStoreCode(), sales.get(i).getStore().getManager().getLastName(), sales.get(i).getStore().getManager().getFirstName()));
                int length = printString.length();
                for (int j = 0; j < 42 - length; j++) {
                    printString.append(" ");
                }
                printString.append(String.format("%-11d$%10.2f", salesCount, (double) Math.round(total * 100) / 100));
                System.out.println(printString);
                total = 0;
                salesCount = 1;
            }
        }

        stores.sort(cmpByStoreId);
        int counter;
        for (Store store : stores) {
            counter = 0;
            for (Sale sale : sales) {
                if (store.getStoreCode().equals(sale.getStore().getStoreCode())) {
                    counter++;
                }
            }
            if (counter == 0) {
                StringBuilder printString = new StringBuilder();
                printString.append(String.format("%s %s, %s", store.getStoreCode(), store.getManager().getLastName(), store.getManager().getFirstName()));
                int length = printString.length();
                for (int j = 0; j < 42 - length; j++) {
                    printString.append(" ");
                }
                printString.append(String.format("%-11d$%10.2f", 0, 0.00));
                System.out.println(printString);
            }
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
            System.out.printf("Customer:\n%s, %s (%s)\n", sale.getCustomer().getLastName(), sale.getCustomer().getFirstName(), sale.getCustomer().emailListToString());
            System.out.printf("\t%s\n\n", sale.getCustomer().getAddress().toString());
            System.out.printf("Sales Person:\n%s, %s, (%s) \n", sale.getSalesperson().getLastName(), sale.getSalesperson().getFirstName(), sale.getSalesperson().emailListToString());
            System.out.printf("\t%s\n\n", sale.getSalesperson().getAddress().toString());

            System.out.println("Item                                                               Total");
            System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-                          -=-=-=-=-=-");

            for (Item item : sale.getItems()) {

                if (item instanceof GiftCard) {
                    GiftCard giftCard = (GiftCard) item;
                    double price = giftCard.calculatePrice();

                    System.out.print(giftCard.getName() + "\n\t");
                    StringBuilder printString = new StringBuilder();
                    printString.append(String.format("(Gift Card #%s)", giftCard.getCode()));
                    int length = printString.length();
                    for (int i = 0; i < 57 - length; i++) {
                        printString.append(" ");
                    }
                    printString.append(String.format("$%10.2f", price));
                    System.out.println(printString);

                } else if (item instanceof Product) {
                    Product product = (Product) item;
                    if (product instanceof NewProduct) {
                        double price = ((NewProduct) product).calculatePrice();

                        System.out.print(product.getName() + "\n\t");
                        StringBuilder printString = new StringBuilder();
                        printString.append(String.format("(New Item #%s %d@$%.2f/ea)", product.getCode(), product.getQuantity(), product.getBasePrice()));
                        int length = printString.length();
                        for (int i = 0; i < 57 - length; i++) {
                            printString.append(" ");
                        }
                        printString.append(String.format("$%10.2f", price));
                        System.out.println(printString);

                    } else if (product instanceof UsedProduct) {
                        double price = ((UsedProduct) product).calculatePrice(); //80 percent of original price

                        System.out.print(product.getName() + "\n\t");
                        StringBuilder printString = new StringBuilder();
                        printString.append(String.format("(Used Item #%s %d@($%.2f -> $%.2f)/ea)", product.getCode(), product.getQuantity(), product.getBasePrice(), ((UsedProduct) product).getReducedPrice()));
                        int length = printString.length();
                        for (int i = 0; i < 57 - length; i++) {
                            printString.append(" ");
                        }
                        printString.append(String.format("$%10.2f", price));
                        System.out.println(printString);
                    }
                } else if (item instanceof Service) {
                    Service service = (Service) item;

                    double price = service.calculatePrice();

                    System.out.print(service.getName() + "\n\t");
                    StringBuilder printString = new StringBuilder();
                    printString.append(String.format("(Service #%s by %s, %s %.2fhrs@$%.2f/hr)", service.getCode(), service.getEmployee().getLastName(), service.getEmployee().getFirstName(), service.getNumHours(), service.getHourlyRate()));
                    int length = printString.length();
                    for (int i = 0; i < 57 - length; i++) {
                        printString.append(" ");
                    }
                    printString.append(String.format("$%10.2f", price));
                    System.out.println(printString);

                } else if (item instanceof Subscription) {
                    Subscription subscription = (Subscription) item;

                    double dayDifference = subscription.getDayDifference();
                    double price = subscription.calculatePrice();

                    System.out.print(subscription.getName() + "\n\t");
                    StringBuilder printString = new StringBuilder();
                    printString.append(String.format("(Subscription #%s %d days@$%.2f/yr)", subscription.getCode(), (int) dayDifference, subscription.getAnnualFee()));
                    int length = printString.length();
                    for (int i = 0; i < 57 - length; i++) {
                        printString.append(" ");
                    }
                    printString.append(String.format("$%10.2f", price));
                    System.out.println(printString);
                }
            }
            System.out.println("                                                             -=-=-=-=-=-");
            System.out.printf("                                                    Subtotal $%10.2f\n", sale.calculateSubTotal());
            System.out.printf("                                                         Tax $%10.2f\n", sale.calculateTax());
            if (sale.getCustomer() instanceof PlatinumMember) {
                System.out.printf("                                           Discount (10.00%%) $%10.2f\n", sale.calculateDiscount());
            } else if (sale.getCustomer() instanceof GoldMember) {
                System.out.printf("                                            Discount (5.00%%) $%10.2f\n", sale.calculateDiscount());
            } else if (sale.getCustomer() instanceof Employee) {
                System.out.printf("                                           Discount (15.00%%) $%10.2f\n", sale.calculateDiscount());
            }
            System.out.printf("                                                 Grand Total $%10.2f\n\n\n", (double) Math.round(sale.calculateGrandTotal() * 100) / 100);

        }
    }
}

