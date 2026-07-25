package com.example.app.repository.book.spec;

import com.example.app.model.Book;
import com.example.app.repository.SpecificationProvider;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    private static final String AUTHOR = "author";

    @Override
    public String getKey() {
        return AUTHOR;
    }

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, cb) -> root.get(AUTHOR)
                .in(Arrays.stream(params).toArray());
    }
}
