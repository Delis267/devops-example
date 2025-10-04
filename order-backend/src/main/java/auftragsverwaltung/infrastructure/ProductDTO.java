package auftragsverwaltung.infrastructure;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

@JsonSerialize
public record ProductDTO(Integer id, String name, BigDecimal price, String currency) {
}
