package com.bookStore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStore.entity.Book;
import com.bookStore.entity.MyBookList;
import com.bookStore.exception.ApiException;
import com.bookStore.repository.BookRepository;
import com.bookStore.repository.MyBookRepository;

@Service
public class MyBookListService {

    @Autowired
    private MyBookRepository mybook;

    @Autowired
    private BookRepository bookRepo;

    // Save book with basic validation
    public void saveMyBooks(MyBookList book) {
        if (book.getName() == null || book.getName().isEmpty()) {
            throw new ApiException("Book name cannot be empty");
        }
        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            throw new ApiException("Author name cannot be empty");
        }
        if (book.getPrice() == null || book.getPrice().isEmpty()) {
            throw new ApiException("Price cannot be empty");
        }

        if (mybook.existsByName(book.getName())) { 
            throw new ApiException("Book with name '" + book.getName() + "' already exists");
        }

        mybook.save(book);
    }

    // Add a book to MyBookList by Book ID
    public void addBookById(int bookId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ApiException("Book with ID " + bookId + " not found"));

        if (mybook.existsByName(book.getName())) {
            throw new ApiException("Book already in MyBookList");
        }

        MyBookList myBook = new MyBookList();
        myBook.setName(book.getName());
        myBook.setAuthor(book.getAuthor());
        myBook.setPrice(book.getPrice());

        mybook.save(myBook);
    }

    // Get all books
    public List<MyBookList> getAllMyBooks() {
        return mybook.findAll();
    }

    // Delete book by ID with exception handling
    public void deleteById(int id) {
        MyBookList book = mybook.findById(id)
                .orElseThrow(() -> new ApiException("Book with ID " + id + " not found"));
        mybook.delete(book);
    }
}