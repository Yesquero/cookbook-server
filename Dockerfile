FROM gradle:latest as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM gcr.io/distroless/java17-debian11
COPY --from=build /home/gradle/src/build/libs/*.jar /app/recipe-book-server.jar
ENTRYPOINT ["java", "-jar", "/app/recipe-book-server.jar"]
