package com.bookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bookStore.entity.Book;
import com.bookStore.services.BookService;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    // Home page
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("book", bookService.getAllBook());
        return "home"; // home.html
    }

    // Show new book registration form
    @GetMapping("/book_register")
    public String showBookRegisterForm(Model model) {
        model.addAttribute("book", new Book());
        return "bookRegister"; // bookRegister.html
    }

    // Save new or updated book
    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        bookService.save(book); // Validations in service (ApiException) will run
        return "redirect:/available_books";
    }

    // Show all available books
    @GetMapping("/available_books")
    public String showAvailableBooks(Model model) {
        model.addAttribute("book", bookService.getAllBook());
        return "bookList"; // bookList.html
    }

    // Show edit book form
    @GetMapping("/editBook/{id}")
    public String editBook(@PathVariable int id, Model model) {
        Book book = bookService.getBookById(id); // ApiException handled globally
        model.addAttribute("book", book);
        return "bookEdit"; // bookEdit.html
    }

    // Delete book
    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteById(id); // ApiException handled globally
        return "redirect:/available_books";
    }
}