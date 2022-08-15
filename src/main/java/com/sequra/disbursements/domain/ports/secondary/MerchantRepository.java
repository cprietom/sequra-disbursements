package com.sequra.disbursements.domain.ports.secondary;

import com.sequra.disbursements.domain.model.Merchant;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MerchantRepository {
    /**
     * Finds a specific merchant.
     * @param merchantId
     * @return
     */
    Merchant findById(String merchantId);

    List<Merchant> findAll();
}
