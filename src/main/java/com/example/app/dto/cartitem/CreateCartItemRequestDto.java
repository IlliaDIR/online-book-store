package com.example.app.dto.cartitem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCartItemRequestDto {
    @NotNull
    @Positive
    private Long bookId;
    @NotNull
    @Positive
    private int quantity;
}
