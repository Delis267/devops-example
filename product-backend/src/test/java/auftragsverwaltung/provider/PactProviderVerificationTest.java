package auftragsverwaltung.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.spring6.PactVerificationSpring6Provider;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import produktkatalog.ProductApp;
import produktkatalog.domain.Product;
import produktkatalog.infrastructure.ProductService;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static produktkatalog.domain.Product.CurrencyCode.EUR;

@Provider("product-backend")
@Consumer("order-backend")
@PactFolder("../order-backend/target/pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProductApp.class)
@ExtendWith(PactVerificationSpring6Provider.class)
class PactProviderVerificationTest {

    @LocalServerPort
    int port;

    @MockitoBean
    ProductService productService;

    @BeforeEach
    void setup(@NotNull PactVerificationContext ctx) {
        ctx.setTarget(new HttpTestTarget("localhost", port, "/product-api"));
    }

    @State("product exists")
    void setupUpProductExists(Map<String, Object> params) {
        Integer id = Integer.valueOf(params.getOrDefault("id", 1).toString());

        System.err.println("ID: "+ id); //ID: 1 found!
        /**
         * 1) Verifying a pact between order-backend and product-backend - GET ein Product das existiert: has status code 200
         *
         *     1.1) status: expected status of 200 but was 404
         *
         *     1.2) body: $ Actual map is missing the following keys: currency, id, name, price
         *
         *         {
         *         -  "currency": "USD",
         *         -  "id": 1,
         *         -  "name": "TestProduct",
         *         -  "price": 19.99
         *         +  "error": "Not Found",
         *         +  "path": "/product-api/products/",
         *         +  "status": 404,
         *         +  "timestamp": "2025-10-07T07:50:53.601+00:00"
         *         }
         */

        when(productService.getProductById(id))
                .thenReturn(Optional.of(new Product(id, "TestProduct", new BigDecimal("19.99"), EUR)));
    }

    @State("product 999 not found")
    void setUpProductNotFound() {
        when(productService.getProductById(999))
                .thenReturn(Optional.empty());
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verify(PactVerificationContext ctx) {
        ctx.verifyInteraction();
    }
}
