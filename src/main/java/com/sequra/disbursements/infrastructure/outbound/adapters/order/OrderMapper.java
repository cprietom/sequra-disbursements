package com.sequra.disbursements.infrastructure.outbound.adapters.order;

import com.sequra.disbursements.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderMapper {

    public Order fromDocument(OrderDocument orderDocument) {
        Order order = new Order();
        order.setMerchantId(orderDocument.getMerchant_id().toString());
        order.setShopperId(orderDocument.getShopper_id().toString());
        order.setPrice(orderDocument.getAmount());
        if (Objects.isNull(orderDocument.getCompleted_at())) {
            order.setStatus(StatusType.NOT_COMPLETED);
        } else {
            order.setStatus(StatusType.COMPLETED);
        }
        order.setCreatedAt(orderDocument.getCreated_at());
        order.setCompletedAt(orderDocument.getCompleted_at());
        return order;
    }
}
