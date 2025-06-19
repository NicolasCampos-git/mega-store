FROM eclipse-temurin:22-jdk AS build

# 1. Seteamos versión como string plano
ENV MAVEN_VERSION=4.0.0-rc-3
ENV MAVEN_HOME=/opt/maven
ENV MAVEN_ARCHIVE=apache-maven-${MAVEN_VERSION}-bin.tar.gz
ENV MAVEN_URL=https://dlcdn.apache.org/maven/maven-4/${MAVEN_VERSION}/${MAVEN_ARCHIVE}

# 2. Instalamos Maven
RUN apt-get update && apt-get install -y curl tar && \
    curl -fSL "$MAVEN_URL" -o /tmp/maven.tar.gz && \
    tar -xzf /tmp/maven.tar.gz -C /opt && \
    mv /opt/apache-maven-${MAVEN_VERSION} /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn

# 3. Compilamos tu proyecto
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 4. Imagen final
FROM eclipse-temurin:22-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
