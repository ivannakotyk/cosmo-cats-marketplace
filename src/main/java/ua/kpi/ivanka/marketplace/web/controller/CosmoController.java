package ua.kpi.ivanka.marketplace.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.ivanka.marketplace.service.CosmoCatService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cosmo")
@RequiredArgsConstructor
public class CosmoController {

    private final CosmoCatService cosmoCatService;
    @GetMapping("/cats")
    public List<String> getCosmoCats() {
        return cosmoCatService.getCosmoCats();
    }

    @GetMapping("/products")
    public List<String> getKittyProducts() {
        return cosmoCatService.getKittyProducts();
    }
}