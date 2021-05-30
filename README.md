# spring-boot-ecommerce-demo

Steps to launch application in your local environment:

1. Checkout code to your local.
2. Import project as gradle project.
3. Wait for some time to download dependencies presnt in build.gradle file.
4. Run EcommerceApplication.java as java application. This will run application at default port 8080

In this project, I have used in memory h2 database which can be replaced with any other relational database. I have used JPARepository to connect java to database. As soon one starts application, it automatically creates tables inside h2 database. URL to access in memory h2 database for this project: http://localhost:8080/h2-ui/

Please update JDBC URL to jdbc:h2:mem:testdb and click "Connect" to login and access database. After login, you can query database to see your cerated resources.

In this project, I have used Springfox-swagger2 V2.9.2. Swagger UI is one of the most popular tools for generating interactive documentation from using OpenAPI document. Swagger UI generates an interactive API console for users to quickly learn about APIs and experiment with requests. Additionally, Swagger UI supports the OpenAPI spec and integrates with other Swagger tooling. With the help of this we will generate API documentation which will be informative, readable, and easy to follow.

Default Swagger API documentation can be accessed at http://localhost:8080/v2/api-docs

Default Swagger API human readable structured documentation can be accessed at http://localhost:8080/swagger-ui.html

Please see documentation folder for more details.

Runtime Environment - Java 1.8, Eclipse 2021.3, Spring Boot 2.5.1-SNAPSHOT
