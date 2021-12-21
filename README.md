# CPSC-67101-programming-assignment

This is the Programming Assignment of the Database Course by Behnam Nikbakhtbideh.

This project is written in Java (backend) and ReactJS (FrontEnd).

## Structure

The project is based on the model in [jhipster-jdl.jdl](jhipster-jdl.jdl) that is in [jdl](https://www.jhipster.tech/jdl/intro) format.

The backend side is implemented in Spring Boot (Java) that is based on the MySql.

This architecture has some benefits including:

1. Having a multi layer architecture by Spring has many benefits itself. For example, Staying independent from database by using Spring Data. For example, I created a version of
   this application in MongoDB before, and the only difference was in domain and repository layers. Meaning that
   other layers including service, dto, and rest are completely independent of the persistence layer.
2. Using AOP that brings lots of features for debugging, logging, and security.
3. Enahnce maintainability, reduce complexity, and increase extensibility by using IoC.
4. Having different environments for development, production and tls.
5. Using liquibase for database change management and migration.
6. Achieving most of the aspects of [12 factor principles](https://dzone.com/articles/12-factor-app-principles-and-cloud-native-microser) of microservice architecture by:
   - Codebase: git + maven
   - Config: YAML
   - Build stages: needs a pipeline on the git that needs a dedicated host.
   - Processes & Concurrency: can run multiple instances of the server independently.
   - Port Binding: there is a port in configuration file for each instance.
   - Dev/prod parity: the only difference is in config.
   - Logs: logback
   - Admin processes: monitoring of the server by logstash, prometheus, and grafana.
7. [OpenAPI Specification](https://swagger.io/specification/)
8. Authentication and Authorization layer by Spring based on [JWT](https://jwt.io/)
9. Service oriented architecture based on REST api.

## Requirements

To run this project, you need to install them before.

Also a python library is used for OCR. To install, run these commands (ubuntu):

```shell
sudo apt install tesseract-ocr
sudo apt install libtesseract-dev
```

## Build

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: It is required to build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools.
You will only need to run this command when dependencies change in [package.json](package.json).

```
npm install
```

I used npm scripts and [Webpack][] as build system.

Run the following commands in two separate terminals to create a development where your browser
auto-refreshes when files change on your hard drive.

```
./mvnw
npm start
```

Npm is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `npm update` and `npm install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `npm help update`.

The `npm run` command will list all of the scripts available to run for this project.

## Building for production

### Packaging as jar

To build the final jar and optimize the shopping application for production, run:

```
./mvnw -Pprod clean verify
```

or:

```
mvn clean package -DskipTests
```

This will concatenate and minify the client CSS and JavaScript files.
To start the server, run this command:

```
java -jar target/*.jar
```

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

[node.js]: https://nodejs.org/
[npm]: https://www.npmjs.com/
[webpack]: https://webpack.github.io/
[browsersync]: https://www.browsersync.io/
[jest]: https://facebook.github.io/jest/
[leaflet]: https://leafletjs.com/
[definitelytyped]: https://definitelytyped.org/
