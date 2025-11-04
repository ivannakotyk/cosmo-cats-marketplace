package ua.kpi.ivanka.marketplace.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ua.kpi.ivanka.marketplace.dto.entity.OrderDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.OrderUpdateDTO;
import ua.kpi.ivanka.marketplace.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> list() {
        return orderService.list();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO get(@PathVariable UUID id) {
        return orderService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO create(@Valid @RequestBody OrderCreateDTO dto) {
        return orderService.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO update(@PathVariable UUID id, @Valid @RequestBody OrderUpdateDTO dto) {
        return orderService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
