package com.sequra.disbursements.infrastructure.outbound.adapters.merchant;

import com.sequra.disbursements.domain.model.Merchant;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MerchantRepositoryAdapter implements com.sequra.disbursements.domain.ports.secondary.MerchantRepository {
    private MerchantRepository merchantRepository;
    private MerchantMapper merchantMapper;

    @Inject
    public MerchantRepositoryAdapter(MerchantRepository merchantRepository, MerchantMapper merchantMapper) {
        this.merchantRepository = merchantRepository;
        this.merchantMapper = merchantMapper;
    }

    @Override
    public Merchant findById(String merchantId) {
        return merchantMapper.fromDocument(merchantRepository.findByMyId(merchantId));
    }

    @Override
    public List<Merchant> findAll() {
        return merchantRepository.findAll().stream().map(merchantMapper::fromDocument).collect(Collectors.toList());
    }
}
