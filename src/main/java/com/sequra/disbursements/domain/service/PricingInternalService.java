package com.sequra.disbursements.domain.service;

import org.springframework.stereotype.Service;

/**
 * Contains the business logic to fulfill this:
 * TODO: These amounts should be customizable (with properties or database)
 *
 * The disbursed amount has the following fee per order:
 * 1% fee for amounts smaller than 50 €
 * 0.95% for amounts between 50€ - 300€
 * 0.85% for amounts over 300€
 *
 */
@Service
class PricingInternalService {
    private static final int SMALL_PURCHACHES_PRICE = 50;
    private static final int MEDIUM_PURCHACHES_PRICE = 300;

    private static final Double SMALL_PURCHASES_FEE = 1.0;
    private static final Double MEDIUM_PURCHASES_FEE = 0.95;
    private static final Double BIG_PURCHASES_FEE = 0.85;

    Double getDisburse(Double amount) {
        Double disburse;
        if (amount < SMALL_PURCHACHES_PRICE) {
            disburse = SMALL_PURCHASES_FEE;
        } else if (amount <= MEDIUM_PURCHACHES_PRICE) {
            disburse = MEDIUM_PURCHASES_FEE;
        } else {
            disburse = BIG_PURCHASES_FEE;
        }
        return disburse;
    }
}
