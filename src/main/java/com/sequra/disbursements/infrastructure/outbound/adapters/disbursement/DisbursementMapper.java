package com.sequra.disbursements.infrastructure.outbound.adapters.disbursement;

import com.sequra.disbursements.domain.model.Disbursement;
import com.sequra.disbursements.domain.model.Merchant;
import com.sequra.disbursements.domain.model.Period;
import com.sequra.disbursements.infrastructure.outbound.adapters.merchant.MerchantMapper;

import com.sequra.disbursements.infrastructure.outbound.adapters.merchant.MerchantRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class DisbursementMapper {
    MerchantRepository merchantRepository;
    MerchantMapper merchantMapper;

    @Inject
    public DisbursementMapper(MerchantRepository merchantRepository, MerchantMapper merchantMapper) {
        this.merchantRepository = merchantRepository;
        this.merchantMapper = merchantMapper;
    }
    public DisbursementDocument toDocument(Disbursement disbursement) {
        DisbursementDocument disbursementDocument = new DisbursementDocument();
        disbursementDocument.setAmount(disbursement.getAmount());
        disbursementDocument.setMerchant_id(disbursement.getMerchant().getId());

        disbursementDocument.setWeek(disbursement.getPeriod().getWeek());
        disbursementDocument.setYear(disbursement.getPeriod().getYear());

        return disbursementDocument;
    }

    public Disbursement fromDocument(DisbursementDocument disbursementDocument) {
        Merchant merchant = merchantMapper.fromDocument(merchantRepository.findById(disbursementDocument.getMerchant_id()).orElse(null));
        Period period = new Period(disbursementDocument.getYear(), disbursementDocument.getWeek());
        return new Disbursement(merchant, period, disbursementDocument.getAmount());
    }
}
