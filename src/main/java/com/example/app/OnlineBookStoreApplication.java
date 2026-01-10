package com.example.app;

import com.example.app.model.Book;
import com.example.app.service.BookService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("book_title");
            book.setAuthor("author_name");
            book.setIsbn("example123");
            book.setPrice(BigDecimal.valueOf(12.99));
            System.out.println(book);
            Book fromDb = bookService.save(book);
            System.out.println(fromDb);
            List<Book> allBooks = bookService.findALl();
            System.out.println(allBooks);
        };
    }
}
