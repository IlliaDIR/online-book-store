package com.example.app.dto.cartitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private Long id;
    private int bookId;
    private String bookTitle;
    private int quantity;
}
