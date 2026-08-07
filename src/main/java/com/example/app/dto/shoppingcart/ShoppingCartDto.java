package com.example.app.dto.shoppingcart;

import com.example.app.dto.cartitem.CartItemDto;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartDto {
    private Long userId;
    private Long shoppingCartId;
    private Set<CartItemDto> cartItems;
}
