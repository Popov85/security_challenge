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