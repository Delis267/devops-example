package produktkatalog.infrastructure;


import produktkatalog.domain.Product;

public interface ProductDatabase {
    Product getProductById(Integer id);
}
