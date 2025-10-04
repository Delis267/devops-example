package auftragsverwaltung.domain;

import auftragsverwaltung.api.OrderService;
import auftragsverwaltung.infrastructure.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductService productService;

    @Override
    public Order findOrder(Integer orderId) {
        return switch (orderId) {
            case 1 -> new Order(List.of(productService.getProductById(1)));
            case 2 -> new Order(List.of(productService.getProductById(1), productService.getProductById(2)));
            default -> throw new IllegalStateException("bad id");
        };
    }

    @Override
    public Order payOrder(Integer orderId) {
        return findOrder(orderId).pay();
    }

    @Override
    public Order cancelOrder(Integer orderId) {
        return findOrder(orderId).cancel();
    }
}
