package com.mgg;

import java.util.Comparator;
import java.util.List;

public class SalesReport {

    public static void generateSalesReport(List<Sale> sales) {

        Comparator<Sale> cmpBySalesPerson = Comparator.comparing(a -> a.getSalesperson().getLastName());

        Comparator<Sale> cmpByStoreCode = Comparator.comparing(a -> a.getStore().getStoreCode());

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
                System.out.printf("%s, %s  %d %.2f\n", sales.get(i).getSalesperson().getLastName(), sales.get(i).getSalesperson().getFirstName(), salesCount, total);
                total = 0;
                salesCount = 1;
            }
        }

        System.out.println("+-----------------------------------------------------+");
        System.out.printf("%d $%10.2f\n", salesCountTotal, (double) (Math.round(grandTotal) * 100) / 100);
        System.out.print("\n\n");



        System.out.println("+----------------------------------------------------------------+");
        System.out.println("| Store Sales Summary Report                                     |");
        System.out.println("+----------------------------------------------------------------+");
        System.out.println("Store      Manager                        # Sales    Grand Total  ");
        sales.sort(cmpByStoreCode);
        int storeSalesCount = 1;
        int storeSalesCountTotal = 0;
        double storeTotal = 0;
        double storeGrandTotal = 0;

        for(int i = 0; i < sales.size(); i++) {
            storeTotal += sales.get(i).calculateGrandTotal();
            storeGrandTotal += sales.get(i).calculateGrandTotal();
            storeSalesCountTotal++;
            if(i + 1 < sales.size() && sales.get(i).getStore().getStoreCode().equals(sales.get(i+1).getStore().getStoreCode())) {
                storeSalesCount++;
            } else {
                System.out.printf("%s %s, %s  %d %.2f\n", sales.get(i).getStore().getStoreCode(), sales.get(i).getStore().getManager().getLastName(), sales.get(i).getStore().getManager().getFirstName(), storeSalesCount, storeTotal);
                storeTotal = 0;
                storeSalesCount = 1;
            }
        }

        System.out.println("+----------------------------------------------------------------+");
        System.out.printf("%d $%10.2f\n", storeSalesCountTotal, (double) (Math.round(storeGrandTotal) * 100) / 100);



        System.out.print("\n\n\n\n");

        //printing out sales and calculating prices
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
                    printString.append(String.format("(Subscription #%s %f days@$%.2f/yr)", subscription.getCode(), dayDifference, subscription.getAnnualFee()));
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
            } else if (sale.getCustomer().getPersonId().equals(sale.getSalesperson().getPersonId())) {

                System.out.printf("                                           Discount (15.00%%) $%10.2f\n", sale.calculateDiscount());
            }
            System.out.printf("                                                 Grand Total $%10.2f\n", sale.calculateGrandTotal());

            System.out.print("\n\n");
        }
    }
}