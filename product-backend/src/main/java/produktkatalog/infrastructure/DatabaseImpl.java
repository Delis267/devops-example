package produktkatalog.infrastructure;

import org.springframework.stereotype.Repository;
import produktkatalog.domain.Product;

import java.math.BigDecimal;

import static produktkatalog.domain.CurrencyCode.EUR;

@Repository
public class DatabaseImpl implements produktkatalog.infrastructure.ProductDatabase {

    @Override
    public Product getProductById(Integer id) {
        return switch (id) {
            case 1 -> new Product(1, "Elektolythe-Lemon", new BigDecimal("19.99"), EUR);
            case 2 -> new Product(2, "Elektolythe-Mint", new BigDecimal("19.99"), EUR);
            case 3 -> new Product(3, "Elektolythe-Natural", new BigDecimal("19.99"), EUR);
            default -> throw new IllegalStateException("bad id");
        };
    }
}
