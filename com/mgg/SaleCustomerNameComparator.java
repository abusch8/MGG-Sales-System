package com.mgg;

import java.util.Comparator;

public class SaleCustomerNameComparator implements Comparator<Sale> {
    @Override
    public int compare(Sale a, Sale b) {
        int cmp = CharSequence.compare(a.getCustomer().getLastName(), b.getCustomer().getLastName());
        if (cmp == 0) {
            cmp = CharSequence.compare(a.getCustomer().getFirstName(), b.getCustomer().getFirstName());
        }
        return cmp;
    }
}
