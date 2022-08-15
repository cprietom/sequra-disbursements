package com.sequra.disbursements.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class DisbursementDTO {
    private String merchantId;
    private Integer year;
    private Integer week;
    private Double amount;
}
