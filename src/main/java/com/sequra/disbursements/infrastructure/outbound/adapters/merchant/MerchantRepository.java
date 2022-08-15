package com.sequra.disbursements.infrastructure.outbound.adapters.merchant;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends MongoRepository<MerchantDocument, String> {
    @Query("{'id': ?0}")
    MerchantDocument findByMyId(String id);
}

