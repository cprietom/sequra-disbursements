package com.sequra.disbursements.utils

import com.sequra.disbursements.domain.model.Disbursement
import com.sequra.disbursements.domain.model.Merchant
import com.sequra.disbursements.domain.model.Period

class Utils {
    static Disbursement createDisbursement() {
        return new Disbursement(createMerchant(), createPeriod(), 2.3);
    }

    static Merchant createMerchant() {
        return new Merchant("4", "merchant1")
    }

    static Period createPeriod() {
        return new Period(2019, 4)
    }
}
