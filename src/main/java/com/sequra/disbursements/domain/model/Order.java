package com.sequra.disbursements.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private ObjectId id;
    private String merchantId; // TODO: Should beof type  Merchant
    private String shopperId; // TODO: Should be of type Shopper
    private StatusType status;
    private Date createdAt;
    private Date completedAt;
    private Double price;
}
