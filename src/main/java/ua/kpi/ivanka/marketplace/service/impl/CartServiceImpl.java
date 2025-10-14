package ua.kpi.ivanka.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kpi.ivanka.marketplace.domain.model.Cart;
import ua.kpi.ivanka.marketplace.dto.entity.CartDTO;
import ua.kpi.ivanka.marketplace.dto.request.CartCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.CartUpdateDTO;
import ua.kpi.ivanka.marketplace.service.mapper.CartMapper;
import ua.kpi.ivanka.marketplace.service.CartService;
import ua.kpi.ivanka.marketplace.web.exception.ResourceNotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper mapper;
    private final Map<UUID, Cart> carts = new ConcurrentHashMap<>();

    @Override
    public CartDTO create(CartCreateDTO dto) {
        Cart entity = mapper.toEntity(dto);
        entity.setId(UUID.randomUUID());
        carts.put(entity.getId(), entity);
        log.info("Created cart {}", entity.getId());
        return mapper.toDto(entity);
    }

    @Override
    public List<CartDTO> list() {
        return carts.values().stream().map(mapper::toDto).toList();
    }

    @Override
    public CartDTO get(UUID id) {
        Cart cart = carts.get(id);
        if (cart == null) throw new ResourceNotFoundException("Cart with ID " + id + " not found");
        return mapper.toDto(cart);
    }

    @Override
    public CartDTO update(UUID id, CartUpdateDTO dto) {
        Cart cart = carts.get(id);
        if (cart == null) throw new ResourceNotFoundException("Cart with ID " + id + " not found");
        mapper.updateEntity(cart, dto);
        return mapper.toDto(cart);
    }

    @Override
    public void delete(UUID id) {
        carts.remove(id);
        log.info("Deleted cart {}", id);
    }
}
