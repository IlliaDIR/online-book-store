package com.example.app.service;

import com.example.app.dto.cartitem.CreateCartItemRequestDto;
import com.example.app.dto.cartitem.UpdateCartItemRequestDto;
import com.example.app.dto.shoppingcart.ShoppingCartDto;
import com.example.app.model.ShoppingCart;
import com.example.app.model.User;

public interface ShoppingCartService {
    ShoppingCart createShoppingCart(User user);

    ShoppingCartDto getShoppingCart();

    ShoppingCartDto addBook(CreateCartItemRequestDto requestDto);

    ShoppingCartDto remove(Long id);

    ShoppingCartDto updateQuantity(Long id, UpdateCartItemRequestDto requestDto);
}
