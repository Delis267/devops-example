# DevOps-Demo Monorepo

Dieses Repo demonstriert ohne echte Business-App eine Testlandschaft für den Kurs DevOps:
- **order-backend** (Spring-Boot)
  - Unit-Tests (Domänentest)
  - MockMVC-ITests (Integtationstest)
  - Pact Consumer (Contracttest)
- **product-backend** (Spring Boot)
  - Pact Provider (Contract-Verification-Test)
- **perf-gatling** (Maven-Modul)
  - Gatling-Simulation -> Login-API im product-backend
- **frontend** #todo
