package ua.kpi.ivanka.marketplace.service;

import ua.kpi.ivanka.marketplace.dto.CartDTO;
import ua.kpi.ivanka.marketplace.dto.request.CartCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.CartUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface CartService {

    CartDTO createCart(CartCreateDTO dto);

    List<CartDTO> listCarts();

    CartDTO getCart(UUID id);

    CartDTO updateCart(UUID id, CartUpdateDTO dto);

    void deleteCart(UUID id);
}
