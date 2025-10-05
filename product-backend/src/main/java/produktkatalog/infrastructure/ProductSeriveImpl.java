package produktkatalog.infrastructure;

import org.springframework.stereotype.Service;
import produktkatalog.domain.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static produktkatalog.domain.CurrencyCode.EUR;

@Service
public class ProductSeriveImpl implements ProductService {

    public static final Product LEMON = new Product(1, "Elektolythe-Lemon", new BigDecimal("19.99"), EUR);
    public static final Product MINT = new Product(2, "Elektolythe-Mint", new BigDecimal("19.99"), EUR);
    public static final Product NATURAL = new Product(3, "Elektolythe-Natural", new BigDecimal("15.99"), EUR);

    public static final Map<Integer, Product> MOCK_PRODUCT_DB;

    static {
        MOCK_PRODUCT_DB = new HashMap<>();
        MOCK_PRODUCT_DB.put(1, LEMON);
        MOCK_PRODUCT_DB.put(2, MINT);
        MOCK_PRODUCT_DB.put(3, NATURAL);
    }

    @Override
    public Product getProductById(Integer id) {
        return Optional.ofNullable(MOCK_PRODUCT_DB.get(id))
                .orElseThrow(() -> new IllegalStateException("bad id"));
    }

    @Override
    public List<Product> getAllProducts() throws InterruptedException {
        Thread.sleep(150);
        return MOCK_PRODUCT_DB.values().stream().toList();
    }
}
