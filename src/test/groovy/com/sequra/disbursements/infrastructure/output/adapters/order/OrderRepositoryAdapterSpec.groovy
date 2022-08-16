package com.sequra.disbursements.infrastructure.output.adapters.order

import com.sequra.disbursements.domain.model.Period
import com.sequra.disbursements.infrastructure.outbound.adapters.order.DateRange
import com.sequra.disbursements.infrastructure.outbound.adapters.order.OrderDocument
import com.sequra.disbursements.infrastructure.outbound.adapters.order.OrderMapper
import com.sequra.disbursements.infrastructure.outbound.adapters.order.OrderRepository
import com.sequra.disbursements.infrastructure.outbound.adapters.order.OrderRepositoryAdapter
import com.sequra.disbursements.utils.Utils
import spock.lang.Specification
import spock.lang.Subject

import java.text.SimpleDateFormat

class OrderRepositoryAdapterSpec extends Specification {
    @Subject
    OrderRepositoryAdapter orderRepositoryAdapter

    OrderRepository orderRepository
    OrderMapper orderMapper

    def setup() {
        orderRepository = Mock(OrderRepository)
        orderMapper = Mock(OrderMapper)
        orderRepositoryAdapter = new OrderRepositoryAdapter(orderRepository, orderMapper)
    }

    def "only completed orders are returned"() {
        given: "a merchant and a period"
        def merchant = Utils.createMerchant()
        def period = new Period(2019, 2)
        DateRange dateRange = new DateRange(period)

        and:
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        1 * orderRepository.findCompletedByMerchantDateRange(merchant.getId(), s.format(dateRange.getInitDate()), s.format(dateRange.getEndDate())) >> [
                new OrderDocument("1", merchant.getId(), "343", 32, s.parse("08/01/2019 05:05:00"), null),
                new OrderDocument("2", merchant.getId(), "343", 32, s.parse("08/12/2018 05:05:00"), s.parse("08/01/2019 05:05:00")),
                new OrderDocument("3", merchant.getId(), "343", 32, s.parse("01/01/2019 05:05:00"), s.parse("10/01/2019 15:05:00"))
        ]

        when:
        def result = orderRepositoryAdapter.findCompleted(merchant, period)

        then:
        result != null
        result.size() == 2
    }
}
