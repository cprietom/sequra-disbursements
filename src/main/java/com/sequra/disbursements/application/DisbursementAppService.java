package com.sequra.disbursements.application;

import com.sequra.disbursements.application.dto.DisbursementDTO;
import com.sequra.disbursements.application.dto.DisbursementsDTO;
import com.sequra.disbursements.application.dto.FilterDTO;
import com.sequra.disbursements.domain.model.Disbursement;
import com.sequra.disbursements.domain.model.Merchant;
import com.sequra.disbursements.domain.model.Period;
import com.sequra.disbursements.domain.service.DisbursementService;
import com.sequra.disbursements.domain.service.MerchantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DisbursementAppService {
    private final DisbursementService disbursementService;
    private final MerchantService merchantService;

    public DisbursementAppService(DisbursementService disbursementService,
                                  MerchantService merchantService) {
        this.disbursementService = disbursementService;
        this.merchantService = merchantService;
    }

    public DisbursementsDTO getDisbursements(FilterDTO filterDTO) {
        if (Objects.isNull(filterDTO.getMerchantId())) {
            return this.getDisbursements(filterDTO.getYear(), filterDTO.getWeek());
        } else {
            return this.getDisbursements(filterDTO.getMerchantId(), filterDTO.getYear(), filterDTO.getWeek());
        }
    }

    private DisbursementsDTO getDisbursements(Integer year, Integer week) {
        Period period = new Period(year, week);
        return this.toDisbursementsDTO(disbursementService.getDisbursements(period));
    }

    private DisbursementsDTO getDisbursements(String merchantId, Integer year, Integer week) {
        Merchant merchant = merchantService.findById(merchantId);
        Period period = new Period(year, week);
        return this.toDisbursementsDTO(disbursementService.getDisbursements(merchant, period));
    }

    public DisbursementsDTO calculateDisbursements(Integer year, Integer week) {
        Period period = new Period(year, week);
        return this.toDisbursementsDTO(disbursementService.calculateDisbursements(period));
    }

    private DisbursementsDTO toDisbursementsDTO(List<Disbursement> disbursements) {
        List<DisbursementDTO> disbursementDTOs = disbursements.stream()
                .map(disbursement -> new DisbursementDTO(disbursement.getMerchant().getId(), disbursement.getPeriod().getYear(), disbursement.getPeriod().getWeek(), disbursement.getAmount()))
                .collect(Collectors.toList());
        return new DisbursementsDTO(disbursementDTOs);
    }
}
