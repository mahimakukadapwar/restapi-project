package com.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStore.entity.Book;
import com.bookStore.repository.BookRepository;
import com.bookStore.exception.ApiException;

@Service
public class BookService {

    @Autowired
    private BookRepository bRepo;

    // Save book with validation
    public void save(Book b) {
        if (b.getName() == null || b.getName().isEmpty()) {
            throw new ApiException("Book name cannot be empty");
        }
        if (b.getAuthor() == null || b.getAuthor().isEmpty()) {
            throw new ApiException("Author name cannot be empty");
        }
        if (b.getPrice() == null || b.getPrice().isEmpty()) {
            throw new ApiException("Price cannot be empty");
        }

        // Optional: check for duplicate book by name
        if (bRepo.existsByName(b.getName())) {  // Make sure BookRepository has this method
            throw new ApiException("Book with name '" + b.getName() + "' already exists");
        }

        bRepo.save(b);
    }

    // Get all books
    public List<Book> getAllBook() {
        return bRepo.findAll();
    }

    // Get book by ID with exception handling
    public Book getBookById(int id) {
        return bRepo.findById(id)
                .orElseThrow(() -> new ApiException("Book with ID " + id + " not found"));
    }

    // Delete book by ID with exception handling
    public void deleteById(int id) {
        Book book = bRepo.findById(id)
                .orElseThrow(() -> new ApiException("Book with ID " + id + " not found"));
        bRepo.delete(book);
    }
}