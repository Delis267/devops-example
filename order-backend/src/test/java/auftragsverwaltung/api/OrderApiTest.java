package auftragsverwaltung.api;

import auftragsverwaltung.domain.Order;
import auftragsverwaltung.infrastructure.ProductDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static auftragsverwaltung.domain.Order.State.PAID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderApiTest {

    public static final ProductDTO TEST_PRODUCT = new ProductDTO(1, "TestProduct", new BigDecimal("19.99"), "EUR");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void getOrderById_returnsOrder() throws Exception {
        Order unpaidOrder = new Order(List.of(TEST_PRODUCT));
        when(orderService.findOrder(1)).thenReturn(Optional.of(unpaidOrder));

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("CREATED"))
                .andExpect(jsonPath("$.totalPrice").value("19.99"))
                .andExpect(jsonPath("$.orderedProducts[0].id").value(TEST_PRODUCT.id()))
                .andExpect(jsonPath("$.orderedProducts[0].name").value(TEST_PRODUCT.name()))
                .andExpect(jsonPath("$.orderedProducts[0].price").value(TEST_PRODUCT.price()));
    }

    @Test
    void wrongId_returnsNotFound() throws Exception {
        when(orderService.payOrder(any())).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/orders/999/pay"))
                .andExpect(status().isNotFound());
    }

    @Test
    void payOrder_returnsPaidOrder() throws Exception {
        Order paidOrder = Mockito.mock(Order.class);
        when(paidOrder.getState()).thenReturn(PAID);
        when(paidOrder.getOrderedProducts()).thenReturn(List.of(TEST_PRODUCT));
        when(orderService.payOrder(1)).thenReturn(Optional.of(paidOrder));

        mockMvc.perform(put("/api/orders/1/pay"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("PAID"));
    }
}
