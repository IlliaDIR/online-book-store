package com.example.app.repository.book;

import com.example.app.model.Book;
import com.example.app.repository.SpecificationProvider;
import com.example.app.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key)
                ).findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Can't find specification provider for key - " + key));
    }
}
