package com.example.app.service.impl;

import com.example.app.dto.cartitem.CreateCartItemRequestDto;
import com.example.app.dto.cartitem.UpdateCartItemRequestDto;
import com.example.app.dto.shoppingcart.ShoppingCartDto;
import com.example.app.exception.EntityNotFoundException;
import com.example.app.mapper.CartItemMapper;
import com.example.app.mapper.ShoppingCartMapper;
import com.example.app.model.Book;
import com.example.app.model.CartItem;
import com.example.app.model.ShoppingCart;
import com.example.app.model.User;
import com.example.app.repository.book.BookRepository;
import com.example.app.repository.cartitem.CartItemRepository;
import com.example.app.repository.shoppingcart.ShoppingCartRepository;
import com.example.app.repository.user.UserRepository;
import com.example.app.service.ShoppingCartService;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Override
    public ShoppingCart createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional(readOnly = true)
    public ShoppingCartDto getShoppingCart() {
        return shoppingCartMapper.toDto(getShoppingCartByUserId(getUserId()));
    }

    @Override
    public ShoppingCartDto addBook(CreateCartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = getShoppingCartByUserId(getUserId());

        Optional<CartItem> itemOptional = shoppingCart.getCartItems().stream()
                .filter(item -> Objects.equals(item.getBook().getId(), requestDto.getBookId()))
                .findFirst();

        if (itemOptional.isPresent()) {
            CartItem cartItem = itemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + requestDto.getQuantity());
        } else {
            Book book = bookRepository.findById(requestDto.getBookId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Can't find book with id: " + requestDto.getBookId()));

            CartItem cartItem = cartItemMapper.toModel(requestDto);
            cartItem.setBook(book);
            cartItem.setShoppingCart(shoppingCart);

            shoppingCart.getCartItems().add(cartItem);
        }
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public ShoppingCartDto remove(Long id) {
        ShoppingCart shoppingCart = getShoppingCartByUserId(getUserId());
        CartItem cartItem = getCartItemById(shoppingCart, id);
        shoppingCart.getCartItems().remove(cartItem);
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public ShoppingCartDto updateQuantity(Long id, UpdateCartItemRequestDto requestDto) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart with id: " + id)
        );
        cartItem.setQuantity(requestDto.getQuantity());
        return shoppingCartMapper.toDto(cartItemRepository.save(cartItem).getShoppingCart());
    }

    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        return principal.getId();
    }

    private CartItem getCartItemById(ShoppingCart shoppingCart, Long id) {
        return shoppingCart.getCartItems().stream()
                .filter(item -> Objects.equals(item.getId(), id))
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException("CartItem with id " + id + " not found")
                );
    }

    private ShoppingCart getShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.getReferenceById(userId);
                    return createShoppingCart(user);
                });
    }
}
