# Cosmo Cats Marketplace

### Overview
**Backend development for “Cosmo Cats Marketplace”** — a REST API with full CRUD operations, data validation, global error handling (RFC 9457), Swagger documentation, and integration with a mock REST client (WireMock).

---

### Author
**Ivanna Kotyk**, group IA-33

---

## Technologies
- **Java 17**, **Spring Boot 3.3.4**
- **Jakarta Bean Validation**
- **MapStruct 1.6.2**
- **Springdoc OpenAPI (Swagger UI)**
- **Lombok**
- **WireMock** (REST mock server)
- **SLF4J** (logging)

## Results
✅ CRUD operations fully implemented  
✅ Validation (including custom annotation `@CosmicWordCheck`)  
✅ Global error handling (RFC 9457 compliant)  
✅ Swagger / OpenAPI API contract  
✅ Integration with WireMock (Docker-based mock service)  
✅ Logging with SLF4J (`@Slf4j`)  
✅ Domain-Driven Design (DDD) architecture successfully applied


### Example: 3rd-Party Rates API
**Endpoint:** `/api/v1/products/rates`  
Response:
```json
{ "USD": 42.0, "EUR": 45.5 }