package pl.polsl.notepay.service;

import pl.polsl.notepay.model.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> saveProducts(List<ProductDto> productDtos, String token);

    List<ProductDto> getOwnProducts(String token);
}
