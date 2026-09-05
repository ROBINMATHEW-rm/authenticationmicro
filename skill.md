# JWT Authentication Rules

* Authentication is handled by the Auth Service.

* Auth Service issues JWT tokens.

* API Gateway validates JWT tokens.

* Do not hardcode JWT secrets in production.

* Use Spring Security for authentication and authorization.

* Keep JWT generation logic inside a dedicated service.

* Keep security configuration separate from controllers.

* Public endpoints such as `/auth/**` should not require JWT.

* Protected Product and Inventory APIs should require a valid JWT.

* Do not require or implement a custom `JwtAuthenticationFilter`. JWT validation should be handled through the configured Spring Security/API Gateway mechanism without introducing a custom authentication filter.

* Do not modify unrelated application functionality, business logic, controllers, services, repositories, database configuration, API contracts, or existing project structure when removing the JWT authentication filter.
