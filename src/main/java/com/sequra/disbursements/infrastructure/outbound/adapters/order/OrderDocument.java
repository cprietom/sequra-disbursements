package com.sequra.disbursements.infrastructure.outbound.adapters.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("orders")
@Getter
@AllArgsConstructor
public class OrderDocument {
    @Id
    private String id;

    private String merchant_id;
    private String shopper_id;
    private Double amount;
    private Date created_at;
    private Date completed_at;
}
