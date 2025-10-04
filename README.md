# Demo Tests Monorepo (Backend/Frontend + CDCT + E2E + Gatling)

Dieses Repo demonstriert ohne echte Business-App eine komplette Testlandschaft:
- **backend-stubs** (Spring Boot Mini-API, Unit-Tests, Pact Consumer, Testcontainers-ready)
- **provider-stub** (Spring Boot Provider, Pact Provider Verification)
- **frontend-stubs** (statische Seiten + Cypress E2E)
- **perf-gatling** (Maven-Modul mit Gatling Simulation)
- **ops** (docker-compose mit WireMock + nginx zum Serven der statischen Frontend-Seiten)
- **.gitlab-ci.yml** (Pipeline: Unit, Contracts, Integration, E2E, Perf)

## Quick Start (lokal)
```bash
# Frontend statisch serven + WireMock
docker compose -f ops/docker-compose.test.yml up -d

# Backend-Tests
cd backend-stubs && mvn -q test
cd ../provider-stub && mvn -q test

# Gatling
cd ../perf-gatling && mvn -q gatling:test
```

## Hinweis
- Nur synthetische Daten. Keine Firmennamen/Personendaten.
- Versionen ggf. anpassen je nach Umgebung.
