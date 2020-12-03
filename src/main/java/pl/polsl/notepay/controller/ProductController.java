package pl.polsl.notepay.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.notepay.model.dto.ProductDto;
import pl.polsl.notepay.service.ProductService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<List<ProductDto>> saveProducts(@ApiIgnore @RequestHeader("Authorization") String token,
                                                   @RequestBody List<ProductDto> productDtos) {
        return ResponseEntity.ok(productService.saveProducts(productDtos, token));
    }

    @GetMapping(value = "/own")
    public ResponseEntity<List<ProductDto>> getOwnProducts(@ApiIgnore @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(productService.getOwnProducts(token));
    }

}
