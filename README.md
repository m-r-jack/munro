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
Run the application and browse to localhost:8080/swagger-ui/


## How to make API requests
### To retrieve all summits
```
http://localhost:8080//summits
```

The following request parameters are supported. All are optional:
* limit : specifies the number of items to be returned. Must be a positive integer or 0. 0 indicates no limit and is
  equivalent to omitting the parameter.
* category : filter by category. Must be one on MUNRO, MUNRO_TOP or EITHER. EITHER will return both munros and munro 
  tops and is equivalent to omitting the category parameter.
* max-height : only return summits below or equal to the specified height in metres. Must be a value that can be 
  parsed to a double (E.g. 1025, 999.25). Negative vales are supported (to handle potential undersea mountains in 
  future data sets).
* min-height : only return summits above or equal to the specified height in metres. Must be a value that can be
  parsed to a double (E.g. 1025, 999.25). Negative vales are supported.
* sort : specifies the order of the returned results. Supported values are
  * height_asc,name_asc : sort by height ascending, then name ascending if two summits have the same height.
  * height_asc,name_desc : sort by height ascending, then name descending if two summits have the same height.
  * height_desc,name_asc : sort by height descending, then name ascending if two summits have the same height.
  * height_desc,name_desc : sort by height descending, then name descending if two summits have the same height.
  * name_asc,height_asc : sort by name ascending, then height ascending if two summits have the same name.
  * name_asc,height_desc : sort by name ascending, then height descending if two summits have the same name.
  * name_desc,height_asc : sort by name descending, then height ascending if two summits have the same name.
  * name_desc,height_desc : sort by name descending, then height descending if two summits have the same name.

### Example request using all request parameters
Retrieve all summits of category MUNRO between 952.1m and 960m inclusive, order by height ascending, then name 
descending if two summits have the same height.
```
http://localhost:8080//summits?limit=5&sort=height_asc,name_asc&category=MUNRO&min-height=952.1&max-height=960
```


  


  






