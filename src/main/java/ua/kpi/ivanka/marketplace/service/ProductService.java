package ua.kpi.ivanka.marketplace.service;

import ua.kpi.ivanka.marketplace.dto.ProductDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductCreateDTO;
import ua.kpi.ivanka.marketplace.dto.request.ProductUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDTO createProduct(ProductCreateDTO dto);

    List<ProductDTO> listProducts();

    ProductDTO getProduct(UUID id);

    ProductDTO updateProduct(UUID id, ProductUpdateDTO dto);

    void deleteProduct(UUID id);

}
