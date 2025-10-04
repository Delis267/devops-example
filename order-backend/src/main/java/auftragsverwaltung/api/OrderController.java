package auftragsverwaltung.api;

import auftragsverwaltung.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PutMapping("/orders/{orderId}/pay")
    public Order pay(@PathVariable("orderId") String orderId) {
        return orderService.payOrder(Integer.parseInt(orderId));
    }

    @PutMapping("/orders/{orderId}/cancel")
    public Order cancel(@PathVariable("orderId") String orderId) {
        return orderService.cancelOrder(Integer.parseInt(orderId));
    }

    @GetMapping("/orders/{orderId}")
    public Order findOrder(@PathVariable("orderId") String orderId) {
        return orderService.findOrder(Integer.parseInt(orderId));
    }

}
