package com.sequra.disbursements.domain.service;

import com.sequra.disbursements.domain.model.Merchant;
import com.sequra.disbursements.domain.ports.secondary.MerchantRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MerchantService {
    private MerchantRepository merchantRepository;

    @Inject
    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public Merchant findById(String merchantId) {
        return merchantRepository.findById(merchantId);
    }
}
