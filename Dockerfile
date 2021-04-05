ARG BUILD_IMAGE=maven:3.6.3-openjdk-15
ARG RUNTIME_IMAGE=openjdk:15-jdk-slim

### Pull all maven dependencies
FROM ${BUILD_IMAGE} as dependencies
COPY pom.xml ./
RUN mvn -B dependency:go-offline

### Build spring boot app using maven
FROM dependencies as build
COPY src ./src
RUN mvn -B clean package

### Run a java process to run a service built in previous stage
FROM ${RUNTIME_IMAGE}
COPY --from=build /target/score-challenge-*.jar /app/service.jar
CMD ["java", "-jar", "/app/service.jar"]