package com.il.orderservice.web;

import com.il.orderservice.entites.Order;
import com.il.orderservice.repositories.OrderRepository;
import com.il.orderservice.restClients.InventoryRestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrdersRestControllers {
    private final OrderRepository orderRepository;
    private final InventoryRestClient inventoryRestClient;

    public OrdersRestControllers(OrderRepository orderRepository, InventoryRestClient inventoryRestClient) {
        this.orderRepository = orderRepository;
        this.inventoryRestClient = inventoryRestClient;
    }

    @GetMapping("/orders")
    public List<Order> findAllOrders() {
        List<Order> allOrders = orderRepository.findAll();
        allOrders.forEach(o -> {
            o.getProductItems().forEach(pi -> {
                pi.setProduct(inventoryRestClient.findProductById(pi.getProductId()));
            });
        });
        return allOrders;
    }

    @GetMapping("/orders/{id}")
    public Order findOrderById(@PathVariable String id) {
        Order order = orderRepository.findById(id).get();
        order.getProductItems().forEach(pi -> {
            pi.setProduct(inventoryRestClient.findProductById(pi.getProductId()));
        });
        return order;
    }
}
