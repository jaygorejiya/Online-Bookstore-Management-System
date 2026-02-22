package com.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookstore.model.*;
import com.bookstore.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    public Order placeOrder(User user, List<OrderItem> items) {

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");

        double total = 0;

        for (OrderItem item : items) {
            total += item.getPrice() * item.getQuantity();
            item.setOrder(order);
        }

        order.setTotalAmount(total);
        order.setOrderItems(items);

        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}