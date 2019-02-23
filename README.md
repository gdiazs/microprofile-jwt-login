# microprofile-jwt-login
This tiny library let's you create a authentication microservice with Microprofile. You should provide your own implementation of UserService interface to tells to the login form where fetch de user data.


By default this library uses JOSE implementation to creates JWT. Has a password validation in order to validate user credentials. Uses BCrypt with salt to verify the encoded password. You can see the dependencies in pom.xml

### Clone the repo and execute 

    mvn install

### On your Microprofile project 

	
	<dependency>
    <groupId>io.gdiazs.jwt</groupId>
    <artifactId>microprofile-jwt-login</artifactId>
    <version>1.0-SNAPSHOT</version>
	</dependency>
 
 ### Create your implementations if UserService or use in memory aproach for testing
 Once you have imported this library in a new Microprofile proyect built with https://start.microprofile.io/
 Just configure a similar class like this. You can provide your own implementation of UserService as well.
 
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

### Configure the Microprofile config properties
Don't forget add this properties

microprofile-config.properties


```java
microprofile.jwt.secret=yoursecrethash
microprofile.jwt.privateKey=/privateKey.pem
microprofile.jwt.algorithm=RS256 or HS256 // depends on this the library uses secret or privateKey
microprofile.jwt.keyID=JWT123
microprofile.jwt.aud=web
microprofile.jwt.iss=https://server.example.com
microprofile.jwt.expiration=30000

```


### Test your auth microservice
    POST http://localhost:port/context/auth
    { 
      "username": "gdiazs",
      "password": "Test1234"
    }
