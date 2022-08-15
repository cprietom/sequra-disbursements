package com.sequra.disbursements.domain.ports.secondary;

import com.sequra.disbursements.domain.model.Disbursement;
import com.sequra.disbursements.domain.model.Merchant;
import com.sequra.disbursements.domain.model.Period;

import java.util.List;

public interface DisbursementRepository {
    List<Disbursement> findAll(Period period);
    List<Disbursement> findAll(Merchant merchant, Period period);
    void save(Disbursement disbursement);
}
