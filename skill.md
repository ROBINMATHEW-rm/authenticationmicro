# JWT Authentication Rules

- Authentication is handled by the Auth Service.
- Auth Service issues JWT tokens.
- API Gateway validates JWT tokens.
- Do not hardcode JWT secrets in production.
- Use Spring Security for authentication and authorization.
- Keep JWT generation logic inside a dedicated service.
- Keep security configuration separate from controllers.
- Public endpoints such as `/auth/**` should not require JWT.
- Protected Product and Inventory APIs should require a valid JWT.