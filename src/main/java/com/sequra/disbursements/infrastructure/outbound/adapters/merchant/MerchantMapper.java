package com.sequra.disbursements.infrastructure.outbound.adapters.merchant;

import com.sequra.disbursements.domain.model.Merchant;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MerchantMapper {
    public Merchant fromDocument(MerchantDocument merchantDocument) {
        if (Objects.isNull(merchantDocument)) {
            return null;
        }
        Merchant merchant = new Merchant();
        merchant.setId(merchantDocument.getId());
        merchant.setName(merchantDocument.getName());
        return merchant;
    }
}
