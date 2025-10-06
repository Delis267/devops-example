package produktkatalog.infrastructure;

import org.springframework.stereotype.Service;
import produktkatalog.domain.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static produktkatalog.domain.Product.from;

@Service
public class ProductSeriveImpl implements ProductService {

    public static final Map<Integer, Product> MOCK_PRODUCT_DB;

    static {
        MOCK_PRODUCT_DB = new HashMap<>();
        MOCK_PRODUCT_DB.put(1, from(1, "Electrolyte-Lemon", "19.99"));
        MOCK_PRODUCT_DB.put(2, from(2, "Electrolyte-Mint", "19.99"));
        MOCK_PRODUCT_DB.put(3, from(3, "Electrolyte-Natural", "15.99"));
    }

    @Override
    public Optional<Product> getProductById(Integer id) {
        return Optional.ofNullable(MOCK_PRODUCT_DB.get(id));
    }

    @Override
    public List<Product> getAllProducts() throws InterruptedException {
        Thread.sleep(150);
        return MOCK_PRODUCT_DB.values().stream().toList();
    }
}
