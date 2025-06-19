FROM eclipse-temurin:22-jdk AS build

ENV MAVEN_VERSION=4.0.0-rc-3
ENV MAVEN_HOME=/opt/maven

RUN apt-get update && apt-get install -y curl tar && \
    curl -fsSL https://dlcdn.apache.org/maven/maven-4/${MAVEN_VERSION}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
      | tar -xz -C /opt && \
    mv /opt/apache-maven-${MAVEN_VERSION} /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
