## User Profile REST Service with AKKA HTTP

### Author
- Martin Kuzdowicz

### requierments
- Java 8
- Scala 2.12.4
- sbt 1.0.4

### run tests

    sbt test
    
### build fat jar

    sbt assembly
    
### run the app form command line

    sbt "runMain com.kuzdowiczm.web.rest.upservice.Application"
    
### deployment

    App can be deployed as a docker container for example on AWS ECS
    In this example the server is embeded in the service as a Akka-HTTP application
    
### initial usage

1. to run example GET after Application started request you will need initial user id

2. to create a new user with associated organisation you will need id of created organisation as we do not have yet endpoint to create organisations

For convenience after the application is started from the command line, it will create some initial data

And initial fake resources ID's will be logged

    creating initial data ...
    initial organisation id => f18952e1-e26b-4d0a-a1b2-e365762a4cd4
    initial user id => bb3888d1-f7f7-47e8-ac71-c47fcb3d2f72
    (Server.scala:33) - user-profiles-service running on /127.0.0.1:8080
 
With that initial data you can execute example requests in the browser or with Postman
 
1. example GET request with initial data: 

    endpoint:
    
    http://localhost:8080/user-profile-service/users/bb3888d1-f7f7-47e8-ac71-c47fcb3d2f72
    
2. example POST request with created organisation:

    endpoint:
    
    http://localhost:8080/user-profile-service/users
    
    body:
    
        {
          "firstname": "name",
          "lastname": "lname",
          "email": "email@mail.com",
          "salutation": "Mr",
          "telephone": "+44 123 123 0000",
          "type": "barrister",
          "orgId": "f18952e1-e26b-4d0a-a1b2-e365762a4cd4",
          "address": { "postcode": "EC2 99Y"}
        }
    
3. example PUT request with created organisation:

    endpoint:
    
    http://localhost:8080/user-profile-service/users
    
    body:
    
        {
          "id": "bb3888d1-f7f7-47e8-ac71-c47fcb3d2f72",
          "firstname": "new-name",
          "lastname": "new-lname",
          "email": "email@mail.com",
          "salutation": "Mr",
          "telephone": "+44 123 123 0000",
          "type": "barrister",
          "orgId": "f18952e1-e26b-4d0a-a1b2-e365762a4cd4",
          "address": { "postcode": "EC2 99Y"}
        }

4. example DELETE request with initial data:

    endpoint:
    
    http://localhost:8080/user-profile-service/users/bb3888d1-f7f7-47e8-ac71-c47fcb3d2f72

    
### Future perspectives / Limitations

- endpoints and functions for handling organisations
- endpoint for all users with pagination
- usage of database not an in memory solution

!!! IMPORTANT

- i had issue couple of times with multithreaded requests on current in memory DB solution, that can be improved in the future

for now i used 

    this.synchronised {}
    
solution in InMemoDB object