package com.sequra.disbursements.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Disbursement {
    private Merchant merchant;
    private Period period;
    private Double amount;
}
