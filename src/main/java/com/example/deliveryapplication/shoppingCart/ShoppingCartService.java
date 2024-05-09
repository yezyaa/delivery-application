package com.example.deliveryapplication.shoppingCart;

import com.example.deliveryapplication.users.SpringDataJPAUsersRepository;
import com.example.deliveryapplication.users.UsersEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShoppingCartService {
    private final SpringDataJPAShoppingCartRepository shoppingCartRepository;
    private final SpringDataJPAUsersRepository usersRepository;

    // 회원별 장바구니 조회
    public ShoppingCartDto findShoppingCart(int userId, int id) {
        UsersEntity usersEntity = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        Optional<ShoppingCartEntity> optionalShoppingCart = shoppingCartRepository.findById(id);
        if (optionalShoppingCart.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다.");

        return ShoppingCartDto.fromEntity(optionalShoppingCart.get());
    }
}
