package auftragsverwaltung.domain;

import auftragsverwaltung.infrastructure.ProductDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class OrderDomainTest {

    @Test
    void total_order_price_is_correct() {
        var meineBestellung = new Order(List.of(//
                new ProductDTO(1, "name1", new BigDecimal("99.99"), "EUR"),//
                new ProductDTO(2, "name2", new BigDecimal("99.99"), "EUR")//
        ));

        assertThat(meineBestellung.getTotalPrice()).isEqualTo(new BigDecimal("199.98"));
    }

    @Test
    void paid_order_cannot_be_cancelled() {
        var meineBestellung = new Order(List.of(new ProductDTO(1, "name", new BigDecimal("99.99"), "EUR")));

        meineBestellung.pay();

        assertThatExceptionOfType(IllegalStateException.class)//
                .isThrownBy(meineBestellung::cancel)//
                .withMessageContaining("Order already paid cannot be canceled");

    }
}
