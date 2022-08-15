package com.sequra.disbursements.infrastructure.outbound.adapters.merchant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("merchants")
@Getter
@Setter
public class MerchantDocument {
    @Id
    private String id;

    private String name;
    private String email;
    private String cif;
}
