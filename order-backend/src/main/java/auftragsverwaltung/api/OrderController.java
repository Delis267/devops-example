package auftragsverwaltung.api;

import auftragsverwaltung.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PutMapping("/orders/{orderId}/pay")
    public ResponseEntity<Order> pay(@PathVariable("orderId") Integer orderId) {
        return ResponseEntity.of(orderService.payOrder(orderId));
    }

    @PutMapping("/orders/{orderId}/cancel")
    public ResponseEntity<Order> cancel(@PathVariable("orderId") Integer orderId) {
        return ResponseEntity.of(orderService.cancelOrder(orderId));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> findOrder(@PathVariable("orderId") Integer orderId) {
        return ResponseEntity.of(orderService.findOrder(orderId));
    }

}
