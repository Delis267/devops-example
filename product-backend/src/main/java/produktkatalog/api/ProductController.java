package produktkatalog.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import produktkatalog.domain.Product;
import produktkatalog.infrastructure.ProductDatabase;

@RestController
@RequestMapping("/product-api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductDatabase productDatabase;

    @GetMapping("/products/{id}")
    public Product get(@PathVariable("id") String id) {
        return productDatabase.getProductById(Integer.parseInt(id));
    }

}
