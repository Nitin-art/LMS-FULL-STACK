package com.library.Controller;

import com.library.model.Book;
import com.library.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // Fetch all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Fetch a single book by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        return ResponseEntity.ok(book);
    }

    // Create a book (only if role is ADMIN)
    @PostMapping
    public ResponseEntity<String> createBook(@RequestBody Book book, @RequestParam String role) {
        if (!role.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admins can add books");
        }
        bookRepository.save(book);
        return ResponseEntity.ok("Book added successfully");
    }

    // Update book (only if role is ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody Book bookDetails, @RequestParam String role) {
        if (!role.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admins can edit books");
        }
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setDescription(bookDetails.getDescription());
        bookRepository.save(book);
        return ResponseEntity.ok("Book updated successfully");
    }

    // Delete book (only if role is ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id, @RequestParam String role) {
        if (!role.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admins can delete books");
        }
        bookRepository.deleteById(id);
        return ResponseEntity.ok("Book deleted successfully");
    }
}
