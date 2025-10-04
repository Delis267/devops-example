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
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "product-backend", pactVersion = PactSpecVersion.V3)
class ProductConsumerPactTest {

    @Pact(consumer = "order-backend", provider = "product-backend")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        PactDslJsonBody body = new PactDslJsonBody()
                .numberValue("id", 1)
                .stringValue("name", "TestProduct")
                .numberValue("price", 19.99)
                .stringValue("currency", "EUR");

        return builder
                .given("product with id 1 exists")
                .uponReceiving("GET /product-api/products/1")
                .path("/product-api/products/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body(body)
                .toPact();
    }

    @Test
    @PactTestFor
    public void verifiesGetProductById(MockServer mockServer) {
        var productService = new ProductServiceImpl(new RestTemplate(), mockServer.getUrl() + "/product-api");

        ProductDTO dto = productService.getProductById(1);

        assertThat(dto.id()).isEqualTo(1);
        assertThat(dto.name()).isEqualTo("TestProduct");
        assertThat(dto.price()).isEqualByComparingTo("19.99");
        assertThat(dto.currency()).isEqualTo("EUR");
    }

}
