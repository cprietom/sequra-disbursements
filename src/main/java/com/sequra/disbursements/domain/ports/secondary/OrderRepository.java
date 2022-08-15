package com.sequra.disbursements.domain.ports.secondary;

import com.sequra.disbursements.domain.model.Merchant;
import com.sequra.disbursements.domain.model.Order;
import com.sequra.disbursements.domain.model.Period;

import java.util.List;

public interface OrderRepository {
    /**
     * Finds all completed orders made in the specified period.
     * @param period
     * @return
     */
    List<Order> findCompleted(Merchant merchant, Period period);
}
