package nn.dgordeev.tls.app.httpsapplication.controller.rest;

import nn.dgordeev.tls.app.httpsapplication.domain.Book;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private static List<Book> books;

    static {
        books = new ArrayList<>();
    }

    @GetMapping
    public ResponseEntity<List<Book>> listBooks() {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(books);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        if (books.contains(book)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(book);
        }
        books.add(book);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(book);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable(name = "bookId") final String id) {
        Book bookFromDb = books.stream()
                .filter(book -> book.getId().equals(UUID.fromString(id)))
                .findFirst()
                .orElseThrow();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(bookFromDb);
    }

    @GetMapping("?byName={bookName}")
    public ResponseEntity<Book> getBookByName(
            @PathVariable(name = "bookName") final String bookName
    ) {
        Book bookFromDb = books.stream()
                .filter(book -> book.getName().equals(bookName))
                .findFirst()
                .orElseThrow();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(bookFromDb);
    }

    @PutMapping("?update={bookName}")
    public ResponseEntity<Book> updateBook(
            @PathVariable(name = "bookName") final String bookName,
            @RequestBody Book updatedBook) {
        Book bookFromDb = books.stream()
                .filter(book -> book.getName().equals(bookName))
                .findFirst()
                .orElseThrow();
        UUID bookId = bookFromDb.getId();
        BeanUtils.copyProperties(updatedBook, bookFromDb);
        bookFromDb.setId(bookId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookFromDb);
    }

    @DeleteMapping("?delete={bookName}")
    public ResponseEntity<UUID> deleteBook(
            @PathVariable(name = "bookName") final String bookName) {
        for (Book book : books) {
            if (book.getName().equalsIgnoreCase(bookName)) {
                books.remove(book);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(book.getId());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UUID.randomUUID());
    }
}
