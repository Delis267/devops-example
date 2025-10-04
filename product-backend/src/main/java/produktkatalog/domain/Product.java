package produktkatalog.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.EnumDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@JsonSerialize
public class Product {
    private int id;
    private String name;
    private BigDecimal price;
    @JsonDeserialize(using = EnumDeserializer.class)
    private CurrencyCode currency;
}
