## User Profile REST Service with AKKA HTTP

### Author
- Martin Kuzdowicz

### requierments
- Java 8
- Scala 2.11
- SBT 0.13

### run tests

    sbt test
    
### build fat jar

    sbt assembly
    
### start server form command line

    sbt "runMain com.kuzdowiczm.web.rest.upservice.Application"
    
### deployment

    App can be deployed as a docker container for example to AWS ECS
    In this example the server is embeded in the service as a Akka-HTTP application
    
### Future perspectives / Limitations