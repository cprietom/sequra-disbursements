package com.sequra.disbursements.infrastructure.outbound.adapters.disbursement;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("disbursements")
@Getter
@Setter
public class DisbursementDocument {
    @Id
    private String id;

    private String merchant_id;
    private Double amount;
    private Integer year;
    private Integer week;
}
