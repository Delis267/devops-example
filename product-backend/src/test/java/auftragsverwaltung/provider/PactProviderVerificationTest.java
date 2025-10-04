package auftragsverwaltung.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import produktkatalog.ProductApp;
import produktkatalog.domain.Product;
import produktkatalog.infrastructure.ProductDatabase;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static produktkatalog.domain.CurrencyCode.EUR;

@Provider("product-backend")
@Consumer("order-backend")
@PactFolder("src/test/resources/pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProductApp.class)
@ExtendWith(PactVerificationSpringProvider.class)
class PactProviderVerificationTest {

    @LocalServerPort
    int port;

    @MockBean
    ProductDatabase productDatabase;

    @BeforeEach
    void setup(PactVerificationContext ctx) {
        ctx.setTarget(new HttpTestTarget("localhost", port));
    }

    @State("product with id 1 exists")
    void product1Exists() {
        when(productDatabase.getProductById(1))
                .thenReturn(new Product(1, "TestProduct", new BigDecimal("19.99"), EUR));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verify(PactVerificationContext ctx) {
        ctx.verifyInteraction();
    }
}
