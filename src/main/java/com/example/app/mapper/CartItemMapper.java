package com.example.app.mapper;

import com.example.app.config.CartItemMapperConfig;
import com.example.app.dto.cartitem.CartItemDto;
import com.example.app.dto.cartitem.CreateCartItemRequestDto;
import com.example.app.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CartItemMapperConfig.class)
public interface CartItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemDto toDto(CartItem cartItem);

    CartItem toModel(CreateCartItemRequestDto cartItemDto);
}
