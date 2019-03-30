# microprofile-jwt-login
## This library was built for Demo purposes it's not recommended at production.

This tiny library let's you create an authentication microservice with Microprofile. You should provide your own implementation of UserService interface to tells to the login service where fetch the user data.


By default this library uses JOSE implementation to creates JWT. Has a password validation in order to validate user credentials. Uses BCrypt with salt to verify the encoded password. You can see the dependencies in pom.xml


### On your Microprofile project 
[![Maven Central](https://img.shields.io/maven-central/v/io.github.gdiazs/microprofile-jwt-login.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.gdiazs%22%20AND%20a:%22microprofile-jwt-login%22)
	
```xml
  <dependency>
    <groupId>io.github.gdiazs</groupId>
    <artifactId>microprofile-jwt-login</artifactId>
    <version>0.0.2</version>
  </dependency>
```
 
 ### Create UserService implementation or use in memory approach for testing
 Once you have imported this library in a Microprofile project built with https://start.microprofile.io/
 Just configure a similar class like this. You can provide your own user service implementation to fetch your user data.
 ```java
    @Dependent
    public class AuthservicesConfig {

      @Produces
      public UserService userService() {

        UserServiceBuilder.userBuilder()
                  .addUser("gdiazs", "$2y$06$HJMVVcT0mBshzc9ZCLtJXuwi0R4CPuKGbJDGVlyGYAt6KnM9UfC6C", "admin", "tester")
                  .addUser("memo", "$2y$06$HJMVVcT0mBshzc9ZCLtJXuwi0R4CPuKGbJDGVlyGYAt6KnM9UfC6C", "developer");

        return UserServiceBuilder.build();
      }


      @Produces
      public PasswordEncoder passwordEncoder() {
        return new PasswordEncoderDefault(6);
      }
    }
```
### Configure the Microprofile config properties
Don't forget add this properties

microprofile-config.properties


```java
microprofile.jwt.secret=yoursecrethash
microprofile.jwt.privateKey=/privateKey.pem
microprofile.jwt.algorithm=RS256 # or HS256 depends on this the library uses secret or privateKey
microprofile.jwt.aud=web
microprofile.jwt.iss=https://server.example.com
microprofile.jwt.expiration=30000
microprofile.jwt.keyID=JWT-MP
```


### Test your auth microservice
    POST http://localhost:port/context/auth
    { 
      "username": "gdiazs",
      "password": "Test1234"
    }
