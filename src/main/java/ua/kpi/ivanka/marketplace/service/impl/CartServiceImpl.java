package ua.kpi.ivanka.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kpi.ivanka.marketplace.domain.model.Cart;
import ua.kpi.ivanka.marketplace.dto.CartDTO;
import ua.kpi.ivanka.marketplace.dto.request.CartCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.CartUpdateDTO;
import ua.kpi.ivanka.marketplace.service.mapper.CartMapper;
import ua.kpi.ivanka.marketplace.service.CartService;
import ua.kpi.ivanka.marketplace.service.exception.CartNotFoundException;
import ua.kpi.ivanka.marketplace.util.IdGenerator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper mapper;
    private final Map<UUID, Cart> carts = new ConcurrentHashMap<>();

    {
        Cart c1 = Cart.builder()
                .id(IdGenerator.generate())
                .productIds(List.of(UUID.randomUUID(), UUID.randomUUID()))
                .totalPrice(25.99)
                .build();

        Cart c2 = Cart.builder()
                .id(IdGenerator.generate())
                .productIds(List.of(UUID.randomUUID()))
                .totalPrice(12.50)
                .build();

        carts.put(c1.getId(), c1);
        carts.put(c2.getId(), c2);

        log.info("Initialized mock cart store with {} items", carts.size());
    }

    @Override
    public CartDTO createCart(CartCreateDTO dto) {
        Cart entity = mapper.toCart(dto);
        entity.setId(IdGenerator.generate());
        carts.put(entity.getId(), entity);
        log.info("Created cart {}", entity.getId());
        return mapper.toCartDTO(entity);
    }

    @Override
    public List<CartDTO> listCarts() {
        log.debug("Listing all carts (count={})", carts.size());
        return carts.values().stream()
                .map(mapper::toCartDTO)
                .toList();
    }

    @Override
    public CartDTO getCart(UUID id) {
        Cart cart = carts.get(id);
        if (cart == null) {
            log.warn("Cart with ID {} not found", id);
            throw new CartNotFoundException(id);
        }
        log.debug("Retrieved cart: {} (ID={})", cart, id);
        return mapper.toCartDTO(cart);
    }

    @Override
    public CartDTO updateCart(UUID id, CartUpdateDTO dto) {
        Cart cart = carts.get(id);
        if (cart == null) {
            log.warn("Cannot update — cart with ID {} not found", id);
            throw new CartNotFoundException(id);
        }

        mapper.updateCart(cart, dto);
        log.info("Updated cart {}", id);
        return mapper.toCartDTO(cart);
    }

    @Override
    public void deleteCart(UUID id) {
        if (carts.remove(id) != null) {
            log.info("Deleted cart {}", id);
        } else {
            log.debug("Cart with ID {} already missing (delete is idempotent)", id);
        }
    }
}
