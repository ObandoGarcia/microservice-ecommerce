#--- Build ---
FROM maven:3.9-eclipse-temurin-25-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

#--- Runtime ---
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar product.jar
ENTRYPOINT ["java", "-jar", "product.jar"]