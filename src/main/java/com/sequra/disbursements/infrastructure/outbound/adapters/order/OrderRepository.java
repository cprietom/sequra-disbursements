package com.sequra.disbursements.infrastructure.outbound.adapters.order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<OrderDocument, String> {
    // TODO: Test this interface to ensure that it returns JUST completed orders within the specified period (@DataMongoTest)
    @Query("{'merchant_id': ?0, 'completed_at': { $gte: ?1, $lt: ?2, $ne: null } }")
    List<OrderDocument> findCompletedByMerchantDateRange(String merchantId, String completedAtInit, String completedAtEnd);
}

