FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean package -Pproduction

CMD ["java", "-jar", "target/fatetle-1.0-SNAPSHOT.jar"]