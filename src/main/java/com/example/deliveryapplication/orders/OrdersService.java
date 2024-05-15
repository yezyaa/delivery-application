package com.example.deliveryapplication.orders;

import com.example.deliveryapplication.cartItems.CartItemsEntity;
import com.example.deliveryapplication.cartItems.SpringDataJPACartItemsRepository;
import com.example.deliveryapplication.menus.MenusEntity;
import com.example.deliveryapplication.menus.SpringDataJPAMenusRepository;
import com.example.deliveryapplication.orderItems.OrderItemsEntity;
import com.example.deliveryapplication.orderItems.SpringDataJPAOrderItemsRepository;
import com.example.deliveryapplication.shoppingCart.ShoppingCartEntity;
import com.example.deliveryapplication.shoppingCart.SpringDataJPAShoppingCartRepository;
import com.example.deliveryapplication.stores.SpringDataJPAStoresRepository;
import com.example.deliveryapplication.stores.StoresEntity;
import com.example.deliveryapplication.users.SpringDataJPAUsersRepository;
import com.example.deliveryapplication.users.UsersEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrdersService {
    private final SpringDataJPAUsersRepository usersRepository;
    private final SpringDataJPAOrdersRepository ordersRepository;
    private final SpringDataJPAShoppingCartRepository shoppingCartRepository;
    private final SpringDataJPACartItemsRepository cartItemsRepository;
    private final SpringDataJPAMenusRepository menusRepository;
    private final SpringDataJPAStoresRepository storesRepository;
    private final SpringDataJPAOrderItemsRepository orderItemsRepository;

    // 주문(결제)
    @Transactional
    public void saveOrder(int userId, OrdersDto dto) {
        UsersEntity usersEntity = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        if (!usersEntity.getRole().equals("회원"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "장바구니에 메뉴를 담을 권한이 없습니다.");

        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."));

        List<CartItemsEntity> cartItemsEntity = cartItemsRepository.findByShoppingCartId(shoppingCartEntity.getId());
        if (cartItemsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "장바구니에 담긴 메뉴를 찾을 수 없습니다.");

        MenusEntity menusEntity = menusRepository.findById(cartItemsEntity.get(0).getMenu().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다."));

        StoresEntity storesEntity = storesRepository.findById(menusEntity.getStore().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "식당을 찾을 수 없습니다."));

        // 주문 총액 확인
        int totalAmount = 0;
        for (CartItemsEntity cartItems : cartItemsEntity)
            totalAmount += cartItems.getPrice();

        // 최소주문금액 예외처리
        if (totalAmount < storesEntity.getMinOrderPrice())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "최소 주문 금액을 충족하지 않습니다.");

        // 배달팁 추가
        int deliveryTip = storesEntity.getDeliveryTip();
        totalAmount += deliveryTip;

        OrdersEntity newOrders = OrdersEntity.builder()
                .user(usersEntity)
                .store(storesEntity)
                .deliveryRequests(dto.getDeliveryRequests())
                .paymentMethod(dto.getPaymentMethod())
                .totalAmount(totalAmount)
                .createdAt(LocalDateTime.now())
                .build();
        ordersRepository.save(newOrders);

        // 주문 아이템 저장
        for (CartItemsEntity cartItems : cartItemsEntity) {
            MenusEntity menuItems = menusRepository.findById(cartItems.getMenu().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu Id " + cartItems.getMenu().getId() + "를 찾을 수 없습니다."));

            OrderItemsEntity newOrderItems = OrderItemsEntity.builder()
                    .order(newOrders)
                    .menu(menuItems)
                    .quantity(cartItems.getQuantity())
                    .price(cartItems.getPrice())
                    .build();
            orderItemsRepository.save(newOrderItems);
        }
        cartItemsRepository.deleteAllByShoppingCartId(shoppingCartEntity.getId());
    }

    // 회원별 주문 전체 조회 (주문 내역 조회)
    public List<OrdersDto> findOrdersByUserId(int userId) {
        UsersEntity usersEntity = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        List<OrdersDto> ordersDto = new ArrayList<>();
        for (OrdersEntity ordersEntity : ordersRepository.findByUserId(userId)) {
            ordersDto.add(OrdersDto.fromEntity(ordersEntity));
        }
        return ordersDto;
    }

    // 회원별 주문 단일 조회
    public OrdersDto findOrderByUserId(int userId, int id) {
        UsersEntity usersEntity = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        OrdersEntity ordersEntity = ordersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."));

        if (ordersEntity.getUser().getId() != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "주문 정보에 접근할 권한이 없습니다.");
        }

        return OrdersDto.fromEntity(ordersEntity);
    }

    // 회원별 주문 취소
    public void deleteOrderByUserId(int userId, int id) {
        UsersEntity usersEntity = usersRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        OrdersEntity ordersEntity = ordersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."));

        if (!ordersEntity.getOrderStatus().equals("대기"))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "주문 상태가 변경되었습니다.");
        ordersRepository.deleteById(id);
    }

    // 사장별 특정 매장 주문 전체 조회
    public List<OrdersDto> findOrdersByStoreIdAndUserId(int userId, int storeId) {
        StoresEntity storesEntity = storesRepository.findById(storeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "식당을 찾을 수 없습니다."));

        if (storesEntity.getUser().getId() != userId)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사장이 소유한 식당이 아닙니다.");

        List<OrdersDto> ordersDto = new ArrayList<>();
        for (OrdersEntity orders : ordersRepository.findByStoreId(storeId)) {
            ordersDto.add(OrdersDto.fromEntity(orders));
        }
        return ordersDto;
    }

    // 사장별 특정 매장 주문 단일 조회
    public OrdersDto findOrderByStoreIdAndUserId(int userId, int storeId, int id) {
        StoresEntity storesEntity = storesRepository.findById(storeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "식당을 찾을 수 없습니다."));

        if (storesEntity.getUser().getId() != userId)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사장이 소유한 식당이 아닙니다.");

        Optional<OrdersEntity> optionalOrders = ordersRepository.findById(id);
        if (optionalOrders.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다.");

        return OrdersDto.fromEntity(optionalOrders.get());
    }

    // 사장별 특정 매장 주문상태(수락/거절) 픽업상태(요청/픽업) 수정
    @Transactional
    public OrdersDto updateOrderStatusByStoreIdAndUserId(int userId, int storeId, int id, OrdersDto dto) {
        StoresEntity storesEntity = storesRepository.findById(storeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "식당을 찾을 수 없습니다."));

        if (storesEntity.getUser().getId() != userId)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사장이 소유한 식당이 아닙니다.");

        Optional<OrdersEntity> optionalOrders = ordersRepository.findById(id);
        if (optionalOrders.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다.");

        OrdersEntity ordersEntity = optionalOrders.get();

        ordersEntity.setOrderStatus(dto.getOrderStatus());
        ordersEntity.setPickupStatus(dto.getPickupStatus());
        ordersEntity.setUpdatedAt(LocalDateTime.now());
        return OrdersDto.fromEntity(ordersRepository.save(ordersEntity));
    }
}
