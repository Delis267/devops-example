package auftragsverwaltung.api;

import auftragsverwaltung.domain.Order;

import java.util.Optional;


public interface OrderService {
    Optional<Order> findOrder(Integer orderId);

    Optional<Order> payOrder(Integer orderId);

    Optional<Order> cancelOrder(Integer orderId);
}
