package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.rampUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ProduktkatalogSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl(Config.BASE_URL + "/product-api")
            .contentTypeHeader("application/json");

    ScenarioBuilder scn = scenario("Lade Produktkatalog-Test")
            .exec(
                    http("Lade Produktkatalog")
                            .get("/products")
                            .check(status().is(200))
            );
    {
        setUp(
                scn.injectOpen(
                        rampUsers(1000).during(60)
                )
        ).protocols(httpProtocol);
    }

}
