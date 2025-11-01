package ua.kpi.ivanka.marketplace.service;

import ua.kpi.ivanka.marketplace.dto.entity.CartDTO;
import ua.kpi.ivanka.marketplace.dto.request.CartCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.CartUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface CartService {
    CartDTO create(CartCreateDTO dto);
    List<CartDTO> list();
    CartDTO get(UUID id);
    CartDTO update(UUID id, CartUpdateDTO dto);
    void delete(UUID id);
}
