package pl.polsl.notepay.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.notepay.exception.WrongRequestException;
import pl.polsl.notepay.model.dto.ProductDto;
import pl.polsl.notepay.model.entity.Product;
import pl.polsl.notepay.model.entity.User;
import pl.polsl.notepay.repository.ProductRepository;
import pl.polsl.notepay.service.ProductService;
import pl.polsl.notepay.util.AuthenticationUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final AuthenticationUtils authenticationUtils;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public List<ProductDto> saveProducts(List<ProductDto> productDtos, String token) {

        User currentUser = authenticationUtils.getUserFromToken(token);

        List<Product> products = productDtos.stream().map(productDto -> {

            if(productDto.getName() == null || "".equals(productDto.getName()) || productDto.getDefaultValue() < 0)
                throw new WrongRequestException("Required fields does not match the requirements");

            return Product.builder()
                    .name(productDto.getName())
                    .defaultValue(productDto.getDefaultValue())
                    .user(currentUser)
                    .build();
        }).collect(Collectors.toList());

        products = productRepository.saveAll(products);
        return products.stream().map(ProductDto::new).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getOwnProducts(String token) {

        return authenticationUtils.getUserFromToken(token)
                .getProducts().stream().map(ProductDto::new).collect(Collectors.toList());
    }

}
