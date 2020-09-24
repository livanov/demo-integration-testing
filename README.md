# Integration Testing Demo

## Motivation

Nowadays most of the code we run in production is not ours - we use someone's code deployed somewhere around - 
persistent storage, messaging, caching, secrets management, external services exposed through an API. All this, makes 
integration testing more and more important - how do we integrate with components outside of our application boundaries?


With this demo project, I want to show an alternative approach to tackle this problem, having numerous integrations with
various software vendors. This demo setup is around a project called `testcontainers` combining containerization and 
testing.

## How to run

#### Prerequisites
* `java` (8 or higher)
* (optional) `docker` 
* (optional) `docker-compose`

#### Build the project and run it locally

```
./mvnw spring-boot:run
```

#### _(optional)_ Create an image and run the app in a container

```
./mvnw spring-boot:build-image
docker run -p 8080:8080 --rm integration-testing:1.0
```

#### _(optional)_ Run dependant components in containers with `docker-compose`

_You could use `docker-compose` to deploy a full-fetched environment with all the features of the demo `postgresql` db,
`redis` cache and a `redis-ui`._

```
docker-compose up -d
```

## Key Takeaways
* The **RIGHT** way to do things is **not always the BEST** way. `Testcontainers` is not a silver bullet and as almost 
anything else in programming, it is a tradeoff that has to be considered thoroughly.
* Don't mock types you don't own (e.g. JPA and Hibernate classes, HTTP clients, Messaging clients, etc.; i.e. 
`EntityManager`, Spring's `Repository` classes, Apache's `HttpClient`, `RestTemplate`, `KafkaTemplate`, etc.)
* `Testcontainers` is especially helpful when you want to test (and develop) against the same version of component that
you use in production.
* `Testcontainers` project has a very small java library dependency footprint, so big projects with a lot of 
dependencies (often called monoliths and famous for their lack of tests) can benefit from introducing integration 
testing through `Testcontainers`, thus reducing the risk of messing up the dependency tree of the project.


## Caveats
* When using the `testcontainers` project, sometimes test time gets increased dramatically. 
  - Possible reasons are:
    * containers that start slowly
    * a new container is created for every test
  - Possible mitigations
    * choose images that start faster
    * tweak images to drop the configuration/initialization of unnecessary features
    * run tests in parallel
    * share container instances between tests (very often this is not a good idea)  
* Modern CICD pipelines use containers to run the build of a project. Hence, the tests are also ran in container. To run
containers within a container we need a specially crafted image known as 
[DinD (docker-in-docker)](https://hub.docker.com/_/docker)
* Theoretically we could use containers to conduct performance testing on our apps. Beware - a lot of pitfalls on the
road.

![image](https://raw.githubusercontent.com/testcontainers/testcontainers-java/master/docs/logo.png)

## Resources:
* [TestContainers project](https://www.testcontainers.org/)
* [TestContainers examples](https://github.com/testcontainers/testcontainers-java/tree/master/examples)
* [Wiremock - embedded mock web server](http://wiremock.org/)
* [MockServer - embedded mock web server](https://www.mock-server.com/)
* [How-to: Start embedded glassfish for integration tests](http://javaevangelist.blogspot.com/2013/01/using-glassfish-311-embedded-with-junit.html)
* [How-to: Test web servlets through mocks](http://mockrunner.github.io/mockrunner)

