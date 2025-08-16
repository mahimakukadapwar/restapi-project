package com.bookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bookStore.entity.MyBookList;
import com.bookStore.services.MyBookListService;

@Controller
public class MyBookListController {

    @Autowired
    private MyBookListService service;

    // Show all books in MyBookList
    @GetMapping("/my_books")
    public String showMyBooks(Model model) {
        model.addAttribute("book", service.getAllMyBooks());
        return "myBooks"; // myBooks.html
    }

    // Add a book to MyBookList
    @GetMapping("/mylist/{id}")
    public String addBookToMyList(@PathVariable int id) {
        service.addBookById(id); // Custom method to fetch Book by id and add to MyBookList
        return "redirect:/my_books";
    }

    // Delete a book from MyBookList
    @GetMapping("/deleteMyList/{id}")
    public String deleteBookFromMyList(@PathVariable int id) {
        service.deleteById(id); // ApiException handled globally
        return "redirect:/my_books";
    }
}