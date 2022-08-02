### Pre-build endpoints (oauth)

#### Token

```
curl --location --request POST 'http://localhost:8080/oauth/token?grant_type=password&username=user&password=password&scope=read'
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
- Copy jks file to /resources dir;
- Adjust application.yaml specifying the given props:

    - key-store: classpath:my_key_store.jks 
    - key-store-password: qwerty123456 
    - key-pair-alias: my_key_store 
    - key-pair-password: qwerty123456