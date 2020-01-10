
## Simple Dog API

Author: Melchor Tatlonghari

## Instructions
1. Replace application.properties with correct jdbc url/s3 details before starting spring-boot
(see docker instructions if no db is available on local)
2. run `mvn clean install` in same directory to build the sources before running
3. you can run the application via running `MainApplication.java` in your IDE or `mvn spring-boot:run` in the 
commandline if you are in the same directory of the pom


### HTTP Methods and documentation
- GET: http://localhost:8080/pet/trigger
    - triggers API, hits external pet API, gets file and uploads to S3 and saves to database
- GET: http://localhost:8080/pet
    - gets ALL pets
- GET: http://localhost:8080/pet/{id}
    - gets a pet given an ID
- GET: http://localhost:8080/pet/breeds
    - gets a list of pet breeds stored in db
- DELETE: http://localhost:8080/pet/{id}
    - deletes S3 files and soft delete pet in database

***NOTE:***
remember to set Accept and Content-Type header as
`application/json`



## Technologies/Libraries/Frameworks used
- Java 8
-  Lombok - removes boilerplate code from java like setters and getters
(Builder, AllArgsConstructor, Data, NoArgConstructor, Slf4j)
- Spring JDBC Template - for simple ORM 
- Spring/SpringBoot - Framework for lightweight API service
- Maven - to handle all dependencies and builds involved
- Jackson - for validating incoming request and response. Serialization/Deserialization of json requests
- (Optional) Docker - to set up database should you not have database installed 

### Known Issues/Design Notes
- currently trigger endpoint just returns "success", to get the new entries a separate GET call has to be made,
this could be improved but for simplicity sake it was done this way
- not much logging was placed and not much testing as well, due to lack of time
- Lombok is syntantic sugar to lessen boilerplate codes, getter and setters could still be used
- we are soft deleting by design to retain data

#### Setup Database on Docker (Optional)
change to docker directory , and just run `docker-compose up` to start mysql with default user/pw