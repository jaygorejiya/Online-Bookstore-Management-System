package com.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.bookstore.model.*;
import com.bookstore.service.*;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/place")
    public String placeOrder(HttpSession session, Model model) {

        List<Book> cart = (List<Book>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        // ⚠ For now we use dummy user (since login not implemented yet)
        User user = userService.findByEmail("test@test.com").orElse(null);

        if (user == null) {
            user = new User();
            user.setName("Test User");
            user.setEmail("test@test.com");
            user.setPassword("1234");
            user.setRole("USER");
            user = userService.registerUser(user);
        }

        List<OrderItem> items = new ArrayList<>();

        for (Book book : cart) {
            OrderItem item = new OrderItem();
            item.setBook(book);
            item.setQuantity(1);
            item.setPrice(book.getPrice());
            items.add(item);
        }

        orderService.placeOrder(user, items);

        session.removeAttribute("cart");

        return "order-success";
    }
}