package auftragsverwaltung.api;

import auftragsverwaltung.domain.Order;


public interface OrderService {
    Order findOrder(Integer orderId);
    Order payOrder(Integer orderId);
    Order cancelOrder(Integer orderId);
}
