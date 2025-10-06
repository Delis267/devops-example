package auftragsverwaltung.provider;

import au.com.dius.pact.core.model.HttpRequest;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.TargetRequestFilter;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import produktkatalog.ProductApp;
import produktkatalog.domain.Product;
import produktkatalog.infrastructure.ProductService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static produktkatalog.domain.Product.CurrencyCode.EUR;

@Provider("product-backend")
@Consumer("order-backend")
@PactFolder("../order-backend/target/pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProductApp.class)
@ExtendWith(PactVerificationSpringProvider.class)
class PactProviderVerificationTest {

    @LocalServerPort
    int port;

    @MockBean
    ProductService productService;

    @BeforeEach
    void setup(@NotNull PactVerificationContext ctx) {
        ctx.setTarget(new HttpTestTarget("localhost", port, "/product-api"));
    }

    @State("product 1 exists")
    void productExists(Map<String, Object> params) {
        Integer id = asInt(params.getOrDefault("id", 1));
        System.err.println(params);
        when(productService.getProductById(id))
                .thenReturn(Optional.of(new Product(id, "TestProduct", new BigDecimal("19.99"), EUR)));
    }

    @State("product 999 not found")
    void productNotFound(Map<String, Object> params) {
        Integer id = asInt(params.getOrDefault("id", 999));
        System.err.println(params);
        when(productService.getProductById(id))
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
