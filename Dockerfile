FROM gradle:7-jdk17 as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /home/gradle/src/build/libs/*.jar /app/recipe-book-server.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/recipe-book-server.jar"]
