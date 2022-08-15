package com.sequra.disbursements.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FilterDTO {
    private Integer year;
    private Integer week;
    private String merchantId;
}
