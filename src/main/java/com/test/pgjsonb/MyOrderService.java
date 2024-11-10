package com.test.pgjsonb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MyOrderService {

    @Autowired
    private MyOrderRepository orderRepository;

    @Transactional
    public MyOrder createOrder(String orderNumber, String customerName, Map<String, Object> extraFields) {
        MyOrder order = new MyOrder();
        order.setOrderNumber(orderNumber);
        order.setCustomerName(customerName);
        order.setExtraFields(extraFields);
        return orderRepository.save(order);
    }

    @Transactional
    public Optional<MyOrder> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public MyOrder updateOrder(Long id, Map<String, Object> extraFields) {
        Optional<MyOrder> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            MyOrder order = optionalOrder.get();
            order.setExtraFields(extraFields);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // 价格范围查询
    public List<MyOrder> findOrdersByPriceRange(Double minPrice, Double maxPrice) {
        return orderRepository.findByPriceRange(minPrice, maxPrice);
    }

    // 价格范围查询+价格倒序
    public List<MyOrder> findByPriceRangeOrderByPriceDesc(Double minPrice, Double maxPrice) {
        return orderRepository.findByPriceRangeOrderByPriceDesc(minPrice, maxPrice);
    }

    /**
     * 多条件查询 使用 @Query
     */
    public List<MyOrder> searchOrders(Double minPrice, Double maxPrice, String beginDate, String endDate, String orderNumber) {
        return orderRepository.searchOrders(minPrice, maxPrice, beginDate, endDate, orderNumber);
    }

//    public List<MyOrder> searchOrders2(Double minPrice, Double maxPrice, String beginDate, String endDate, String orderNumber) {
//        Specification<MyOrder> spec = Specification.where(null);
//
////        if (minPrice != null && maxPrice != null) {
////            spec = spec.and(MyOrderSpecification.withPriceRange(minPrice, maxPrice));
////        }
//        if (minPrice != null)
//            spec = spec.and(MyOrderSpecification.withPriceGe(minPrice));
//        if (maxPrice != null)
//            spec = spec.and(MyOrderSpecification.withPriceLt(maxPrice));
//        if (beginDate != null && endDate != null) {
//            spec = spec.and(MyOrderSpecification.withDateRange(beginDate, endDate));
//        }
//        if (orderNumber != null) {
//            spec = spec.and(MyOrderSpecification.withOrderNumber(orderNumber));
//        }
//
//        return orderRepository.findAll(spec);
//    }
//
//    public static class MyOrderSpecification {
//
//        public static Specification<MyOrder> withPriceRange(Double minPrice, Double maxPrice) {
//            return (root, query, criteriaBuilder) -> {
//                if (minPrice != null && maxPrice != null) {
//                    return criteriaBuilder.between(
//                            criteriaBuilder.function(
//                                    "CAST", Double.class,
//                                    criteriaBuilder.function("jsonb_extract_path_text", String.class,
//                                            root.get("extraFields"),
//                                            criteriaBuilder.literal("price"))), minPrice, maxPrice);
//                }
//                return null;
//            };
//        }
//
//        public static Specification<MyOrder> withPriceGe(Double price) {
//            return (root, query, criteriaBuilder) -> {
//                if (price != null) {
//                    Expression<Double> priceAsDouble = criteriaBuilder.function(
//                            "CAST", Double.class,
//                            criteriaBuilder.function("jsonb_extract_path_text", String.class,
//                                    root.get("extraFields"),
//                                    criteriaBuilder.literal("price"))
//                    );
//                    return criteriaBuilder.ge(priceAsDouble, price);
//                }
//                return null;
//            };
//        }
//
//        public static Specification<MyOrder> withPriceLt(Double price) {
//            return (root, query, criteriaBuilder) -> {
//                if (price != null) {
//                    return criteriaBuilder.lt(
//                            criteriaBuilder.function(
//                                    "CAST", Double.class,
//                                    criteriaBuilder.function("jsonb_extract_path_text", String.class,
//                                            root.get("extraFields"),
//                                            criteriaBuilder.literal("price"))), price);
//                }
//                return null;
//            };
//        }
//
//        public static Specification<MyOrder> withDateRange(String beginDate, String endDate) {
//            return (root, query, criteriaBuilder) -> {
//                if (beginDate != null && endDate != null) {
//                    return criteriaBuilder.between(
//                            root.get("extraFields").get("date"), beginDate, endDate);
//                }
//                return null;
//            };
//        }
//
//        public static Specification<MyOrder> withOrderNumber(String orderNumber) {
//            return (root, query, criteriaBuilder) -> {
//                if (orderNumber != null) {
//                    return criteriaBuilder.like(
//                            root.get("orderNumber"), "%" + orderNumber + "%");
//                }
//                return null;
//            };
//        }
//    }

}
