package com.sequra.disbursements.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class DisbursementsDTO {
    private List<DisbursementDTO> disbursements;
}
