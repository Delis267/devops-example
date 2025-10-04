package auftragsverwaltung.domain;

import auftragsverwaltung.infrastructure.ProductDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class Order {
    private final List<ProductDTO> orderedProducts;
    private final BigDecimal totalPrice;
    private final LocalDateTime orderDate;
    private State state;

    public enum State {CREATED, PAID, CANCELLED}

    public Order(List<ProductDTO> products) {
        this.state = State.CREATED;
        this.orderedProducts = products;
        this.orderDate = LocalDateTime.now();
        this.totalPrice = orderedProducts.stream().map(ProductDTO::price).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    Order pay() {
        if (state != State.CREATED) throw new IllegalStateException("bad state");
        state = State.PAID;
        return this;
    }

    Order cancel() {
        if (state == State.PAID) throw new IllegalStateException("Order already paid cannot be canceled");
        state = State.CANCELLED;
        return this;
    }
}
