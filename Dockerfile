# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY erp-core/pom.xml erp-core/
COPY erp-hr/pom.xml erp-hr/
COPY erp-inventory/pom.xml erp-inventory/
COPY erp-sales/pom.xml erp-sales/
COPY erp-accounting/pom.xml erp-accounting/
COPY erp-reporting/pom.xml erp-reporting/
COPY erp-application/pom.xml erp-application/
# Download dependencies
RUN mvn dependency:go-offline -B

COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/erp-application/target/erp-application-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
