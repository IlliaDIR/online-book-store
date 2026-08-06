package com.example.app.controller;

import com.example.app.dto.cartitem.CreateCartItemRequestDto;
import com.example.app.dto.cartitem.UpdateCartItemRequestDto;
import com.example.app.dto.shoppingcart.ShoppingCartDto;
import com.example.app.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping cart")
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "Get shopping cart")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartDto getShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    @Operation(summary = "Add a book to a shopping cart")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartDto addBook(
            @RequestBody @Valid CreateCartItemRequestDto requestDto
    ) {
        return shoppingCartService.addBook(requestDto);
    }

    @Operation(summary = "Update book quantity")
    @PutMapping("/items/{cartItemId}")
    public ShoppingCartDto updateShoppingCart(
            @PathVariable Long cartItemId,
            @RequestBody @Valid UpdateCartItemRequestDto requestDto
    ) {
        return shoppingCartService.updateQuantity(cartItemId, requestDto);
    }

    @Operation(summary = "Remove book from shopping cart")
    @DeleteMapping("/items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ShoppingCartDto deleteItemFromShoppingCart(@PathVariable Long cartItemId) {
        return shoppingCartService.remove(cartItemId);
    }
}


