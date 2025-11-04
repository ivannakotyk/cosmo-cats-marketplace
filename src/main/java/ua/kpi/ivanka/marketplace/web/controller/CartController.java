package ua.kpi.ivanka.marketplace.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.kpi.ivanka.marketplace.dto.entity.CartDTO;
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
    @ResponseStatus(HttpStatus.OK)
    public List<CartDTO> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CartDTO get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartDTO create(@Valid @RequestBody CartCreateDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CartDTO update(@PathVariable UUID id, @Valid @RequestBody CartUpdateDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
