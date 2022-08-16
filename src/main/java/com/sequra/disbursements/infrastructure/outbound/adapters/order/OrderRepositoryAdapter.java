package com.sequra.disbursements.infrastructure.outbound.adapters.order;

import com.sequra.disbursements.domain.model.Merchant;
import com.sequra.disbursements.domain.model.Order;
import com.sequra.disbursements.domain.model.Period;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class OrderRepositoryAdapter implements com.sequra.disbursements.domain.ports.secondary.OrderRepository {
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;

    @Inject
    public OrderRepositoryAdapter(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }
    @Override
    public List<Order> findCompleted(Merchant merchant, Period period) {
        DateRange dateRange = new DateRange(period);
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        List<OrderDocument> orderDocuments =
                orderRepository.findCompletedByMerchantDateRange(
                        merchant.getId(), s.format(dateRange.getInitDate()), s.format(dateRange.getEndDate()));
        return orderDocuments.stream()
                .filter(orderDocument -> Objects.nonNull(orderDocument.getCompleted_at()))
                .map(orderDocument -> orderMapper.fromDocument(orderDocument))
                .collect(Collectors.toList());
    }
}
