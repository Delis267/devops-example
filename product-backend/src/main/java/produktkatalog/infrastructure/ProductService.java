package produktkatalog.infrastructure;


import produktkatalog.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> getProductById(Integer id);

    List<Product> getAllProducts() throws InterruptedException;
}
