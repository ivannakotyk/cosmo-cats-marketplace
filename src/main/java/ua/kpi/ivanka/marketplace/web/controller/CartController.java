package ua.kpi.ivanka.marketplace.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kpi.ivanka.marketplace.dto.CartDTO;
import ua.kpi.ivanka.marketplace.dto.request.CartCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.CartUpdateDTO;
import ua.kpi.ivanka.marketplace.service.CartService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    @GetMapping
    public ResponseEntity<List<CartDTO>> listCarts() {
        List<CartDTO> carts = service.listCarts();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> getCart(@PathVariable UUID id) {
        CartDTO cart = service.getCart(id);
        return ResponseEntity.ok(cart);
    }

    @PostMapping
    public ResponseEntity<CartDTO> createCart(@Valid @RequestBody CartCreateDTO dto) {
        CartDTO createdCart = service.createCart(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCart);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDTO> updateCart(@PathVariable UUID id,
                                              @Valid @RequestBody CartUpdateDTO dto) {
        CartDTO updatedCart = service.updateCart(id, dto);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable UUID id) {
        service.deleteCart(id);
        return ResponseEntity.noContent().build();
    }
}
