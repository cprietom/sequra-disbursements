package com.sequra.disbursements.domain.service;

import com.sequra.disbursements.application.dto.DisbursementDTO;
import com.sequra.disbursements.domain.model.Disbursement;
import com.sequra.disbursements.domain.model.Merchant;
import com.sequra.disbursements.domain.model.Order;
import com.sequra.disbursements.domain.model.Period;
import com.sequra.disbursements.domain.ports.secondary.DisbursementRepository;
import com.sequra.disbursements.domain.ports.secondary.MerchantRepository;
import com.sequra.disbursements.domain.ports.secondary.OrderRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

@Service
public class DisbursementService {
    private final OrderRepository orderRepository;

    private final DisbursementRepository disbursementRepository;

    private final MerchantRepository merchantRepository;

    private final PricingInternalService pricingInternalService;

    @Inject
    public DisbursementService(OrderRepository orderRepository,
                               DisbursementRepository disbursementRepository,
                               MerchantRepository merchantRepository,
                               PricingInternalService pricingInternalService) {
        this.orderRepository = orderRepository;
        this.disbursementRepository = disbursementRepository;
        this.merchantRepository = merchantRepository;
        this.pricingInternalService = pricingInternalService;
    }

    /**
     * Use case:
     * Expose the disbursements for a given merchant on a given week.
     * If no merchant is provided return for all of them.
     * @param period
     * @return
     */
    public List<Disbursement> getDisbursements(Period period) {
        Objects.requireNonNull(period);

        return disbursementRepository.findAll(period);
    }

    public List<Disbursement> getDisbursements(Merchant merchant, Period period) {
        Objects.requireNonNull(merchant);
        Objects.requireNonNull(period);

        return disbursementRepository.findAll(merchant, period);
    }

    /**
     * Use case:
     * Calculate and persist the disbursements per merchant on a given week.
     * We disburse only orders which status is completed.
     * As the calculations can take some time it should be isolated and be able
     * to run independently of a regular web request, for instance by running a background job.
     *
     * @param period
     */
    public List<Disbursement> calculateDisbursements(Period period) {
        Objects.requireNonNull(period);

        validatePeriod(period);

        // Retreive all merchants
        List<Merchant> merchants = merchantRepository.findAll();

        List<Disbursement> allDisbursements = new ArrayList<>();
        for (Merchant merchant : merchants) {
            // Retrieve all completed orders in the period for the given merchant
            List<Order> orders = orderRepository.findCompleted(merchant, period);

            // Calculate disbursements for the obtained orders
            Double weeklyFee = orders.stream().mapToDouble(order -> pricingInternalService.getDisburse(order.getPrice())).sum();
            Disbursement disbursement = new Disbursement(merchant, period, weeklyFee);

            // Persist calculated disbursement
            disbursementRepository.save(disbursement);

            allDisbursements.add(disbursement);
        }

        return allDisbursements;
    }

    private Period toPeriod(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Integer week = calendar.get(Calendar.WEEK_OF_YEAR);
        Integer year = calendar.get(Calendar.YEAR);
        return new Period(year, week);
    }
    private void validatePeriod(Period period) throws RuntimeException {
        Period todayPeriod = toPeriod(new Date());
        if (todayPeriod.compareTo(period) <= 0) {
            throw new RuntimeException("You are trying to use a period in the future!");
        }
    }
}
