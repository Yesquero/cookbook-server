FROM gradle:latest as builder
COPY . /usr/src/app
RUN cd /usr/src/app && ./gradlew build

FROM gcr.io/distroless/java17-debian11
COPY --from=builder /usr/src/app/build/libs/recipe-book-0.0.1-SNAPSHOT.jar /usr/app/recipe-book.jar
ENTRYPOINT ["java", "-jar", "/usr/app/recipe-book.jar"]