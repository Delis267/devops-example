package auftragsverwaltung.contract;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import auftragsverwaltung.infrastructure.ProductDTO;
import auftragsverwaltung.infrastructure.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "product-backend", pactVersion = PactSpecVersion.V3)
class ProductConsumerPactTest {

    @Pact(consumer = "order-backend", provider = "product-backend")
    public RequestResponsePact getProduct_ok(PactDslWithProvider builder) {
        Integer id = 1;
        PactDslJsonBody body = new PactDslJsonBody()
                .numberType("id", id)
                .stringType("name", "TestProduct")
                .numberType("price", 19.99)
                .stringMatcher("currency", "EUR|USD");

        return builder
                .given("product exists", Map.of("id", id))
                .uponReceiving("GET ein Product das existiert")
                .pathFromProviderState("/products/${id}", "/products/%d".formatted(id))
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body(body)
                .toPact();
    }

    @Pact(consumer = "order-backend", provider = "product-backend")
    public RequestResponsePact getProduct_notFound(PactDslWithProvider builder) {
        return builder
                .given("product 999 not found")
                .uponReceiving("GET /products/999")
                .path("/products/999")
                .method("GET")
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getProduct_ok")
    public void verifiesGetProductById(MockServer mockServer) {
        var productService = new ProductServiceImpl(new RestTemplate(), mockServer.getUrl());

        ProductDTO dto = productService.getProductById(1);

        assertThat(dto.id()).isEqualTo(1);
        assertThat(dto.name()).isEqualTo("TestProduct");
        assertThat(dto.price()).isEqualByComparingTo("19.99");
        assertThat(dto.currency()).containsAnyOf("EUR", "USD");
    }

    @Test
    @PactTestFor(pactMethod = "getProduct_notFound")
    public void verifiesGetProductNotFound(MockServer mockServer) {
        var svc = new ProductServiceImpl(new RestTemplate(), mockServer.getUrl());
        assertThatExceptionOfType(HttpClientErrorException.NotFound.class)
                .isThrownBy(() -> svc.getProductById(999));
    }

}
