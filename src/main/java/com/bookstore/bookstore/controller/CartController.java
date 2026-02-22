package com.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private BookService bookService;

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {

        Book book = bookService.getBookById(id);

        List<Book> cart = (List<Book>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        cart.add(book);
        session.setAttribute("cart", cart);

        return "redirect:/books";
    }

    @GetMapping
    public String viewCart(HttpSession session, Model model) {

        List<Book> cart = (List<Book>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/cart";
    }
}