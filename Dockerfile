# Etapa 1: Build con Maven 4 + Java 22
FROM eclipse-temurin:22-jdk AS build

# Instalar Maven 4.0.0 manualmente
ENV MAVEN_VERSION=4.0.0
ENV MAVEN_HOME=/opt/maven
RUN apt-get update && apt-get install -y curl tar && \
    curl -fsSL https://dlcdn.apache.org/maven/maven-4/${MAVEN_VERSION}/apache-maven-${MAVEN_VERSION}-bin.tar.gz | tar -xz -C /opt && \
    mv /opt/apache-maven-${MAVEN_VERSION} /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar el jar
FROM eclipse-temurin:22-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
