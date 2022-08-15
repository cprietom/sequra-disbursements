package com.sequra.disbursements.infrastructure.outbound.adapters.disbursement;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisbursementRepository extends MongoRepository<DisbursementDocument, String> {
    @Query("{'year': ?0, 'week': ?1}")
    List<DisbursementDocument> findByWeek(Integer year, Integer week);

    @Query("{'merchant_id': ?0, 'year': ?1, 'week': ?2}")
    List<DisbursementDocument> findByMerchantAndWeek(String merchant_id, Integer year, Integer week);
}

