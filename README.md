## Munro Api

A simple REST  API that provides data about munros and munro tops.



## Installation

Ensure you are using JDK version 11

Clone this repository.

cd to the root directory of the project.

Build the project and run the tests as follows: 
```
./mvnw clean package
````
Once successfully built, you can run the service either with Maven or with an executable jar.

### To run the application with Maven
```
./mvnw spring-boot:run
```


### To run the application with executable JAR

```
./mvnw clean package
java -jar target/munro-0.0.1-SNAPSHOT.jar
```


## To view Swagger API docs

Run the application and browse to localhost:8090/swagger-ui.html

