FROM --platform=linux/x86_64 eclipse-temurin:17-jdk-alpine as build

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src

RUN --mount=type=cache,target=/root/.m2,rw ./mvnw package -DskipTests

FROM --platform=linux/x86_64 eclipse-temurin:17-jre-alpine

COPY --from=build target/resilient-boot-0.1-SNAPSHOT.jar resilient-boot.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "resilient-boot.jar"]
