package com.test.pgjsonb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyOrderRepository extends JpaRepository<MyOrder, Long>, JpaSpecificationExecutor<MyOrder> {

    // 通过 extraFields 中的 price 的范围来查询 order
    @Query(value = "SELECT * FROM my_order WHERE (extra_fields->>'price')::numeric BETWEEN :minPrice AND :maxPrice", nativeQuery = true)
    List<MyOrder> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    @Query(value = "SELECT * FROM my_order WHERE (extra_fields->>'price')::numeric BETWEEN :minPrice AND :maxPrice ORDER BY (extra_fields->>'price')::numeric DESC", nativeQuery = true)
    List<MyOrder> findByPriceRangeOrderByPriceDesc(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    /**
     * 多条件查询 使用 @Query
     */
    @Query(value = "SELECT * FROM my_order o " +
            "WHERE (:minPrice IS NULL OR (o.extra_fields->>'price')::numeric >= :minPrice) " +
            "AND (:maxPrice IS NULL OR (o.extra_fields->>'price')::numeric <= :maxPrice) " +
            "AND (:beginDate IS NULL OR (o.extra_fields->>'date') >= :beginDate) " +
            "AND (:endDate IS NULL OR (o.extra_fields->>'date') <= :endDate) " +
            "AND (:orderNumber IS NULL OR o.order_number LIKE %:orderNumber%)",
            nativeQuery = true)
    List<MyOrder> searchOrders(@Param("minPrice") Double minPrice,
                             @Param("maxPrice") Double maxPrice,
                             @Param("beginDate") String beginDate,
                             @Param("endDate") String endDate,
                             @Param("orderNumber") String orderNumber);

}
