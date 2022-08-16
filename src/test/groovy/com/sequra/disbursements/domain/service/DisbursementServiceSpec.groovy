package com.sequra.disbursements.domain.service

import com.sequra.disbursements.domain.model.Merchant
import com.sequra.disbursements.domain.model.Order
import com.sequra.disbursements.domain.model.Period
import com.sequra.disbursements.domain.ports.secondary.DisbursementRepository
import com.sequra.disbursements.domain.ports.secondary.MerchantRepository
import com.sequra.disbursements.domain.ports.secondary.OrderRepository
import com.sequra.disbursements.utils.Utils
import org.bson.types.ObjectId
import spock.lang.Specification
import spock.lang.Subject

import java.text.SimpleDateFormat
import java.time.LocalDateTime

class DisbursementServiceSpec extends Specification {
    @Subject
    DisbursementService service

    OrderRepository orderRepository
    DisbursementRepository disbursementRepository
    MerchantRepository merchantRepository
    PricingInternalService pricingInternalService

    def setup() {
        orderRepository = Mock(OrderRepository)
        disbursementRepository = Mock(DisbursementRepository)
        merchantRepository = Mock(MerchantRepository)
        pricingInternalService = new PricingInternalService()
        service = new DisbursementService(
                orderRepository,
                disbursementRepository,
                merchantRepository,
                pricingInternalService)
    }

    def "retreiving all disbursements "() {
        given: "a merchant and a period"
        def period = new Period(2020, 2)
        def disbursements = [Utils.createDisbursement(), Utils.createDisbursement()]

        and: "mocked ports"
        1 * disbursementRepository.findAll(period) >> disbursements

        when: "getting disbursements"
        def response = service.getDisbursements(period)

        then: "disbursements found"
        response == disbursements
    }

    def "calculate disbursements properly"() {
        given: "a period"
        Period period = new Period(2018, 1)

        and: "several merchants"
        def merchant1 = new Merchant("1", "merchant1")
        def merchant2 = new Merchant("2", "merchant2")
        def merchants = [merchant1, merchant2]
        1 * merchantRepository.findAll() >> merchants

        and: "completed orders to those merchants"
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        def ordersMerchant1 = [
                new Order(ObjectId.get(), merchant1.getId(), "refe", s.parse("01/01/2018 11:50:55"), s.parse("01/01/2018 00:00:00"), 43),
                new Order(ObjectId.get(), merchant1.getId(), "refe", s.parse("01/01/2018 12:50:55"), s.parse("05/01/2018 11:50:55"), 308)
        ]
        1 * orderRepository.findCompleted(merchant1, period) >> ordersMerchant1
        1 * orderRepository.findCompleted(merchant2, period) >> []

        when: "calculation process launched"
        def response = service.calculateDisbursements(period)

        then: "disbursements persisted"
        response.size() == 2
        response.get(0).getAmount() == 1.85
        response.get(1).getAmount() == 0
    }
}