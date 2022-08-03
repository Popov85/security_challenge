## Spring OAuth example
Info:
https://docs.spring.io/spring-security-oauth2-boot/docs/2.0.0.RC2/reference/htmlsingle/
### Pre-build endpoints (oauth)

#### Token

```
curl --location --request POST 'http://localhost:8080/oauth/token?grant_type=password&username=user&password=password'
```
Scopes by default, (specify if needed!):
```
scope = read write
```

#### Refresh token
```
curl --location --request POST 'http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55X2lkIjoxLCJ1c2VyX2lkIjoyLCJ1c2VyX25hbWUiOiJibGFjayIsImF0aSI6Im9wZTctSnkxbGRpNmdUVzFZWW5RUjNBekwycyIsImNvbXBhbnkiOiJJQk0iLCJleHAiOjE2NTk0MzgyNzUsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiUXRlUmZHbmFSVXBJX2lrNnlzSGNWTm8yZEFnIiwiY2xpZW50X2lkIjoiY2xpZW50SWQifQ.bFLMy8oCvSntPIrm7teI66PMzQGJYU55QrJx0FIV9oqnOWVPcUzlhrZtsw65SPyhUHW1h0XshfWlkptScK_DPnbOt9zz0QKP3PKR40kSNJm-LNVJk5c46xCUNQhfb8jyGlKIRr9MFOj5fPmAvuJZ5DAzmUkjXM1Q1jd0OyYyqEREV2HsiHlZfxbR4ZX0_iWvycVX5Ha2izCVwcyCX_N34CchJPTfDaIgGt4hK-xl17oYWe_TgksOCuCLElxL5Lxz2aw242EGX4SE3VHTMKoZ_S4oyLVL3cp_NKkgKX9jetA-G95s5DSFhz0LRSGWrHcFzDFRPr8qMBgwyU1TR5Ydog' \
--data-raw ''
```

#### Generating jks key pair

```
keytool -genkeypair -alias my_key_store -keyalg RSA -keypass qwerty123456 -keystore my_key_store.jks -storepass qwerty123456
```
More: https://docs.oracle.com/javase/8/docs/technotes/tools/windows/keytool.html

Source: https://www.youtube.com/watch?v=sYXILbgpknM

###### Steps:

- Invoke a command above with your params;
- Answer additional questions from CMD;
- Copy .jks file to /resources dir;
- Adjust application.yaml specifying the given props:
    - key-store: classpath:my_key_store.jks 
    - key-store-password: qwerty123456 
    - key-pair-alias: my_key_store 
    - key-pair-password: qwerty123456

###### General considerations:

- As of OAuth 2.1, the OAuth’s Resource Owner Password Credentials (ROPC) grant type is now deprecated, and its use is discouraged by the OAuth security best practices.
- Despite this, many found it an excellent fit, and the only practical way to do any OAuth2.0 authorisation beyond the most basic JWT tokens in a simple access model.
- Client's creds and settings are placed in application.yaml (maybe better to put it in DB);
- Pre-Basic filter is designed for cases when we only have a single SPA client (own client) and not store there clientId && client secret; otherwise client should send it in basic auth header!
- In the model, a user can have multiple roles, resources can demand different roles;
- Refresh token store is in-memory, for better scalability it is better to replace with Redis store or the like;
- JWT token is enriched with additional info like companyId, company name, etc. for better info about whom a user is;
- User's registration, email confirmation, forgot password, recovery password, two-factor auth functionality is out of the scope!
- For more overwhelming solutions, please see CIAM-s like Auth0, AWS Cognito, etc.

