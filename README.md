### Pre-build endpoints (oauth)

#### Token

```
curl --location --request POST 'http://localhost:8080/oauth/token?grant_type=password&username=user&password=password&scope=read'
```

#### Refresh token
```
curl --location --request POST 'http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=...' \
--header 'Authorization: Basic Y2xpZW50SWQ6c2VjcmV0' \
--data-raw ''
```

Basic auth. pair: 
```
cleintId + secret
```