package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class LoginSimulation extends Simulation {
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8081/api") // Basis-URL anpassen!
            .contentTypeHeader("application/json");

    ScenarioBuilder scn = scenario("LoginLoadTest")
            .exec(
                    http("Login Request")
                            .post("/login")
                            .check(status().is(200))
                            .check(jsonPath("$.token").exists())
            );

    {
        setUp(
                scn.injectOpen(
                        rampUsers(50).during(10) // 50 User über 10 Sekunden
                )
        ).protocols(httpProtocol);
    }
}
