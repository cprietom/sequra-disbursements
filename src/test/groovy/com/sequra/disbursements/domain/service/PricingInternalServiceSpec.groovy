package com.sequra.disbursements.domain.service

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class PricingInternalServiceSpec extends Specification {
    @Subject
    PricingInternalService service

    def setup() {
        service = new PricingInternalService()
    }

    @Unroll
    def "test pricing"() {
        when:
        def result = service.getDisburse(amount)

        then:
        result == resultOk

        where:
        amount  || resultOk
        10      || 1.0
        49      || 1.0
        50      || 0.95
        300     || 0.95
        301     || 0.85
    }
}
