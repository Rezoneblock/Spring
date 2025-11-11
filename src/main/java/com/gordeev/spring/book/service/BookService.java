package com.gordeev.spring.book.service;

import com.gordeev.spring.base.exception.InvalidPatchFieldException;
import com.gordeev.spring.base.exception.ResourceNotFoundException;
import com.gordeev.spring.book.entity.BookEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.*;

@Service
public class BookService {
    private static final Set<String> ALLOWED_FIELDS = Set.of("title", "description");

    static List<BookEntity> bookStorage = new ArrayList<>();

    public BookService() {
        fillStorage();
    }

    public void fillStorage() {
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            BookEntity book = new BookEntity();
            book.setId(i);
            book.setTitle("Book #" + random.nextInt(100, 999));
            book.setDescription("Lorem ipsum something something");
            bookStorage.add(book);
        }
    }

    public List<BookEntity> all() {
        return bookStorage;
    }

    public Optional<BookEntity> byId(Integer id) {
        return bookStorage.stream().filter((book -> book.getId().equals(id))).findFirst();
    }

    public BookEntity create(String title, String description) {
        BookEntity book = new BookEntity();
        book.setId(bookStorage.size());
        book.setTitle(title);
        book.setDescription(description);
        bookStorage.add(book);
        return book;
    }

    public BookEntity edit(BookEntity book) {
        BookEntity oldBookEntity = byId(book.getId()).orElseThrow(ResourceNotFoundException::new);
        oldBookEntity.setTitle(book.getTitle());
        oldBookEntity.setDescription(book.getDescription());
        return oldBookEntity;
    }

    public Boolean delete(Integer id) {
        Optional<BookEntity> book = byId(id);
        if (book.isEmpty()) {
            return false;
        }
        bookStorage.remove(book.get());
        return true;
    }

    public Optional<BookEntity> editPart(Integer id, Map<String, String> fields) {
        Optional<BookEntity> optionalBookEntity = byId(id);
        if (optionalBookEntity.isEmpty()) return Optional.empty();

        BookEntity book = optionalBookEntity.get();
        for (String key : fields.keySet()) {
            if(!ALLOWED_FIELDS.contains(key)) {
                throw new InvalidPatchFieldException(key);
            }
            switch (key) {
                case "title" -> book.setTitle(fields.get(key));
                case "description" -> book.setDescription(fields.get(key));
            }
        }

        return Optional.of(book);
    }

}
