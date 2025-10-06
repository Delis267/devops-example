package auftragsverwaltung.domain;

import auftragsverwaltung.api.OrderService;
import auftragsverwaltung.infrastructure.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductService prodService;

    @Override
    public Optional<Order> findOrder(Integer orderId) {
        return switch (orderId) {
            case 1 -> Optional.of(new Order(List.of(prodService.getProductById(1))));
            case 2 -> Optional.of(new Order(List.of(prodService.getProductById(1), prodService.getProductById(2))));
            default -> Optional.empty();
        };
    }

    @Override
    public Optional<Order> payOrder(Integer orderId) {
        return findOrder(orderId).map(Order::pay);
    }

    @Override
    public Optional<Order> cancelOrder(Integer orderId) {
        return findOrder(orderId).map(Order::cancel);
    }
}
