package com.gordeev.spring.book.controller;

import com.gordeev.spring.base.exception.ResourceNotFoundException;
import com.gordeev.spring.book.entity.BookEntity;
import com.gordeev.spring.book.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookApiController {
    private final BookService bookService;

    public BookApiController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String ok() {
        return "ok";
    }

    @GetMapping("/api/v1/book")
    public List<BookEntity> all() {
        return bookService.all();
    }

    @GetMapping("/api/v1/book/{id}")
    public BookEntity byId(@PathVariable Integer id) {
        return bookService.byId(id).orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping("/api/v1/book")
    public BookEntity create(@RequestBody BookEntity request) {
        return bookService.create(request.getTitle(), request.getDescription());
    }

    @PutMapping("/api/v1/book/{id}")
    public BookEntity edit(@PathVariable Integer id, @RequestBody BookEntity request) {
        return bookService.edit(request).orElseThrow(ResourceNotFoundException::new);
    }
}
