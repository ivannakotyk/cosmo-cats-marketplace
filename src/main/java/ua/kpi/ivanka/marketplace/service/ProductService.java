package ua.kpi.ivanka.marketplace.service;

import ua.kpi.ivanka.marketplace.dto.entity.ProductDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDTO create(ProductCreateDTO dto);

    List<ProductDTO> list();

    ProductDTO get(UUID id);

    ProductDTO update(UUID id, ProductUpdateDTO dto);

    void delete(UUID id);
}
