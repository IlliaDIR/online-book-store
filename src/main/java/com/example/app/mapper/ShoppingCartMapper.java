package com.example.app.mapper;

import com.example.app.config.ShoppingCartMapperConfig;
import com.example.app.dto.shoppingcart.ShoppingCartDto;
import com.example.app.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = ShoppingCartMapperConfig.class,
        uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(source = "id", target = "shoppingCartId")
    @Mapping(source = "user.id", target = "userId")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    ShoppingCart toModel(ShoppingCartDto shoppingCartDto);
}
