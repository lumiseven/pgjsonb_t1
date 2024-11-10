package com.test.pgjsonb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class MyOrderController {

    @Autowired
    private MyOrderService orderService;

    @PostMapping
    public MyOrder createOrder(@RequestBody MyOrderRequest orderRequest) {
        return orderService.createOrder(orderRequest.getOrderNumber(), orderRequest.getCustomerName(), orderRequest.getExtraFields());
    }

    @GetMapping("/{id}")
    public Optional<MyOrder> getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @PutMapping("/{id}")
    public MyOrder updateOrder(@PathVariable Long id, @RequestBody Map<String, Object> extraFields) {
        return orderService.updateOrder(id, extraFields);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    // 价格范围
    @GetMapping("/price-range")
    public List<MyOrder> getOrdersByPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        return orderService.findOrdersByPriceRange(minPrice, maxPrice);
    }

    // 价格范围+价格倒序
    @GetMapping("/price-range-sorted")
    public List<MyOrder> getOrdersByPriceRangeByPriceDesc(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        return orderService.findByPriceRangeOrderByPriceDesc(minPrice, maxPrice);
    }

    /**
     * 多条件查询 使用 @Query
     */
    @GetMapping("/search")
    public List<MyOrder> searchOrders(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String beginDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String orderNumber) {
        return orderService.searchOrders(minPrice, maxPrice, beginDate, endDate, orderNumber);
    }

//    @GetMapping("/search2")
//    public List<MyOrder> searchOrders2(
//            @RequestParam(required = false) Double minPrice,
//            @RequestParam(required = false) Double maxPrice,
//            @RequestParam(required = false) String beginDate,
//            @RequestParam(required = false) String endDate,
//            @RequestParam(required = false) String orderNumber) {
//        return orderService.searchOrders2(minPrice, maxPrice, beginDate, endDate, orderNumber);
//    }

}
