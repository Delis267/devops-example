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
import java.math.BigInteger;
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

    @State("product 1 exists")
    void setupUpProductExists() {
        when(productService.getProductById(1))
                .thenReturn(Optional.of(new Product(1, "TestProduct", new BigDecimal("19.99"), EUR)));
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


    private static Integer asInt(Object value) {
        if (value instanceof Integer i) return i;
        if (value instanceof Long l) return l.intValue();
        if (value instanceof BigInteger bi) return bi.intValue();
        if (value instanceof Number n) return n.intValue();
        return Integer.parseInt(String.valueOf(value));
    }

}
