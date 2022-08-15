package com.sequra.disbursements.infrastructure.outbound.adapters.disbursement;

import com.sequra.disbursements.domain.model.Disbursement;
import com.sequra.disbursements.domain.model.Merchant;
import com.sequra.disbursements.domain.model.Period;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DisbursementRepositoryAdapter implements com.sequra.disbursements.domain.ports.secondary.DisbursementRepository {
    private DisbursementRepository disbursementRepository;
    private DisbursementMapper disbursementMapper;

    @Inject
    public DisbursementRepositoryAdapter(DisbursementRepository disbursementRepository, DisbursementMapper disbursementMapper) {
        this.disbursementRepository = disbursementRepository;
        this.disbursementMapper = disbursementMapper;
    }

    @Override
    public List<Disbursement> findAll(Period period) {
        return disbursementRepository.findByWeek(period.getYear(), period.getWeek()).stream()
                .map(disbursementDocument -> disbursementMapper.fromDocument(disbursementDocument))
                .collect(Collectors.toList());
    }

    @Override
    public List<Disbursement> findAll(Merchant merchant, Period period) {
        return disbursementRepository.findByMerchantAndWeek(merchant.getId(), period.getYear(), period.getWeek()).stream()
                .map(disbursementDocument -> disbursementMapper.fromDocument(disbursementDocument))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Disbursement disbursement) {
        if (this.findAll(disbursement.getMerchant(), disbursement.getPeriod()).isEmpty()) {
            disbursementRepository.save(disbursementMapper.toDocument(disbursement));
        }
    }
}
