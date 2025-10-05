package produktkatalog.infrastructure;


import produktkatalog.domain.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(Integer id);

    List<Product> getAllProducts() throws InterruptedException;
}
