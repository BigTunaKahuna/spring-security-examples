# Explanation

## Short walkthrough

- We have configured both the authorization and resource server to act as independent units and so we will need each instance to run at the same time, but on different ports. For a unified authorization and resource server check this [example](https://github.com/BigTunaKahuna/spring-security-examples/tree/master/security).

- I used eclipse to develop the project, but IntelliJ should work too without any configuration. To add this into eclipse simply select **File>Import** and search for existing maven project. Then you can simply start the projects.

- This project uses the following dependencies (7+ inclusive are added to adress newer version of spring boot):

  1. **spring-boot-starter-security**
  2. **spring-boot-starter-web**
  3. **spring-security-jwt**
  4. **spring-security-oauth2**
  5. **spring-security-autoconfigure**
  6. **spring-boot-devtools**
  7. **jaxb-api**
  8. **jaxb-core**
  9. **jaxb-impl**
  10. **activation**

- One issue I am going to adress is how to get the authorization code and access token respectively.

  To get the authorization code you have to simply access: <http://localhost:8081/oauth/authorize?grant_type=authorization_code&response_type=code&client_id=client&scope=read> and login with the default user created, where:

  | username | password |
  | -------- | -------- |
  | username | password |

  After a successful login you can see a code parameter in your url:  
   ![Imgur](https://i.imgur.com/q0jgfsh.png)

  Keep that code, because you need to make a request using postman, curl or whatever you like to use. I used **Talend Api Tester**, a chrome extension for better visibility.

  ***

  The url needed for the access token is **<http://localhost:8081/oauth/token>** the rest of the information shall be configured the same as in the following photo, the only exception is the **code** parameter. You have to replace it with your own code from the previous step. If all works well you shall receive a **200 response**. One important aspect that I barely found is to use the header **Authorization: Basic Y2xpZW50OnNlY3JldA==**, where the encrypted text after Basic is simply **client:secret** that is encoded in a **Base64**.

  ![Imgur](https://i.imgur.com/F780Epk.png)

  ***

Now we can simply use the access token from the previous request to access resource server specific paths. One important aspect before making the request is to change/add the header
**Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VybmFtZSIsInNjb3BlIjpbInJlYWQiXSwib3JnYW5pemF0aW9uIjoidXNlcm5hbWUiLCJleHAiOjE1NzMwMDc4OTAsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIxMmVhNmI4NC0yMzMwLTRmZjAtOTBhZS1kMDFjMDBjMzU4YTMiLCJjbGllbnRfaWQiOiJjbGllbnQifQ.DXOZniBvyete9co89xdg5_ATz1qc3JmcBZS9x0ee_Hw**

> The encoded token is a jwt from the access token request.

![Imgur](https://i.imgur.com/ODcWclf.png)
