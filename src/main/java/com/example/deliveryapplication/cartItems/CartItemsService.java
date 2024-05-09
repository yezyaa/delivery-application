package com.example.deliveryapplication.cartItems;

import com.example.deliveryapplication.menus.MenusEntity;
import com.example.deliveryapplication.menus.SpringDataJPAMenusRepository;
import com.example.deliveryapplication.shoppingCart.ShoppingCartEntity;
import com.example.deliveryapplication.shoppingCart.SpringDataJPAShoppingCartRepository;
import com.example.deliveryapplication.users.SpringDataJPAUsersRepository;
import com.example.deliveryapplication.users.UsersEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartItemsService {
    private final SpringDataJPACartItemsRepository cartItemsRepository;
    private final SpringDataJPAUsersRepository usersRepository;
    private final SpringDataJPAShoppingCartRepository shoppingCartRepository;
    private final SpringDataJPAMenusRepository menusRepository;

    // 회원별 장바구니 메뉴 담기
    public void saveMenuItems(int userId, CartItemsDto dto) {
        UsersEntity usersEntity = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        if (!usersEntity.getRole().equals("회원"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "장바구니에 메뉴를 담을 권한이 없습니다.");

        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."));

        MenusEntity menusEntity = menusRepository.findById(dto.getMenuId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다."));

        if (menusEntity.getStatus().equals("품절"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 메뉴는 현재 품절입니다.");

        Optional<CartItemsEntity> existingMenuItem = cartItemsRepository.findByShoppingCartIdAndMenuId(shoppingCartEntity.getId(), dto.getMenuId());
        if (existingMenuItem.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "장바구니에 같은 메뉴가 존재합니다. 수량을 수정하세요.");

        int totalPrice = menusEntity.getPrice() * dto.getQuantity();

        CartItemsEntity newMenuItems = CartItemsEntity.builder()
                .shoppingCart(shoppingCartEntity)
                .menu(menusEntity)
                .quantity(dto.getQuantity())
                .price(totalPrice)
                .build();
        cartItemsRepository.save(newMenuItems);
    }

    // 회원별 장바구니 메뉴 수량 수정
    public CartItemsDto updateMenusQuantity(int id, CartItemsDto dto) {
        Optional<CartItemsEntity> optionalCartItems = cartItemsRepository.findById(id);
        if (optionalCartItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다.");
        }

        MenusEntity menusEntity = menusRepository.findById(optionalCartItems.get().getMenu().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다."));

        int totalPrice =  menusEntity.getPrice() * dto.getQuantity();

        CartItemsEntity cartItemsEntity = optionalCartItems.get();
        cartItemsEntity.setQuantity(dto.getQuantity());
        cartItemsEntity.setPrice(totalPrice);
        return CartItemsDto.fromEntity(cartItemsRepository.save(cartItemsEntity));
    }

    // 회원별 장바구니 특정 메뉴 삭제
    public void deleteMenuItem(int userId, int id) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."));

        Optional<CartItemsEntity> optionalCartItems = cartItemsRepository.findById(id);
        if (optionalCartItems.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니에 메뉴를 찾을 수 없습니다.");
        cartItemsRepository.deleteById(id);
    }

    // 회원별 장바구니 메뉴 전체 삭제
    @Transactional
    public void deleteAllMenuItems(int userId) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."));

        cartItemsRepository.deleteAllByShoppingCartId(shoppingCartEntity.getId());
    }











}
