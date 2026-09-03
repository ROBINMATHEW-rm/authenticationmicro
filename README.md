# Auth Service — JWT Authentication

Spring Boot microservice that issues and validates JWT tokens using Spring Security.

---

## Running the service

```bash
./mvnw spring-boot:run
```

The service starts on `http://localhost:8080`.

---

## Endpoints

### Register a new user
```
POST /auth/register
Content-Type: application/json

{
  "username": "alice",
  "password": "secret"
}
```
Returns `201 Created` with a JWT token.  
Returns `409 Conflict` if the username already exists.

---

### Login
```
POST /auth/login
Content-Type: application/json

{
  "username": "alice",
  "password": "secret"
}
```
Returns `200 OK` with a JWT token.  
Returns `401 Unauthorized` on bad credentials.

**Response (both endpoints):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### Using the token
Pass the token in the `Authorization` header on all protected requests:
```
Authorization: Bearer <token>
```

---

## Default dev user

A seeded user is available out of the box (in-memory only):

| Username | Password  |
|----------|-----------|
| `admin`  | `admin123` |

> This user is lost on restart. Register new users via `/auth/register`.

---

## Quick test with curl

```bash
# 1. Register
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"secret"}'

# 2. Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"secret"}'

# 3. Access a protected endpoint (replace <token> with the value from step 2)
curl http://localhost:8080/some/protected \
  -H "Authorization: Bearer <token>"
```

---

## Configuration

| Property | Env variable | Default | Description |
|---|---|---|---|
| `jwt.secret` | `JWT_SECRET` | dev default | Hex-encoded 256-bit HMAC key |
| `jwt.expiration-ms` | — | `86400000` | Token lifetime in ms (24 hours) |

> **Production:** always set `JWT_SECRET` via environment variable. Never use the default key.

---

## Project structure

```
src/main/java/com/authenticationmicro/authenticationmicro/
├── AuthenticationmicroApplication.java   # Entry point
├── config/
│   └── SecurityConfig.java               # Spring Security config
├── controller/
│   └── AuthController.java               # /auth/login, /auth/register
├── dto/
│   ├── AuthRequest.java                  # Request body
│   └── AuthResponse.java                 # Response body (token)
├── filter/
│   └── JwtAuthenticationFilter.java      # Validates Bearer token per request
└── service/
    ├── AppUserDetailsService.java         # In-memory user store
    └── JwtService.java                    # Token generation & validation
```

---

## Notes

- User store is **in-memory** (`ConcurrentHashMap`). Swap `AppUserDetailsService` with a JPA-backed implementation when connecting a real database.
- Sessions are **stateless** — no HTTP session is created.
- CSRF protection is **disabled** (JWT-based API, no browser session).
