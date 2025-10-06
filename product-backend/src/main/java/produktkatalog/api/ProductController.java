package produktkatalog.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import produktkatalog.domain.Product;
import produktkatalog.infrastructure.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product-api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> get(@PathVariable("id") Integer id) {
        return ResponseEntity.of(productService.getProductById(id));//
    }

    @GetMapping("/products")
    public List<Product> getAll() throws InterruptedException {
        return productService.getAllProducts();
    }
}
