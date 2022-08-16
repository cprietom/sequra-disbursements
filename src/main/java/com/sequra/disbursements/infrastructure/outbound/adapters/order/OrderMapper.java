package com.sequra.disbursements.infrastructure.outbound.adapters.order;

import com.sequra.disbursements.domain.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order fromDocument(OrderDocument orderDocument) {
        Order order = new Order();
        order.setMerchantId(orderDocument.getMerchant_id());
        order.setShopperId(orderDocument.getShopper_id());
        order.setPrice(orderDocument.getAmount());
        order.setCreatedAt(orderDocument.getCreated_at());
        order.setCompletedAt(orderDocument.getCompleted_at());
        return order;
    }
}
