#
# Compile, build and package as single 'fat' JAR with Maven

FROM curlimages/curl:8.2.1 AS download
ARG OTEL_AGENT_VERSION="1.32.0"
RUN curl --silent --fail -L "https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${OTEL_AGENT_VERSION}/opentelemetry-javaagent.jar" \
    -o "$HOME/opentelemetry-javaagent.jar"

FROM adoptopenjdk/openjdk16:alpine AS build

ARG APP_VERSION=2.1.7

LABEL Name="Java SpringBoot Demo App" Version=2.1.7
LABEL org.opencontainers.image.source = "https://github.com/benc-uk/java-demoapp"

WORKDIR /build 
COPY .mvn ./.mvn
COPY mvnw ./mvnw
COPY pom.xml .
COPY src ./src

RUN ./mvnw -ntp clean package -Dapp.version=$APP_VERSION -DskipTests -Dmaven.test.skip=true -Dcheckstyle.skip=true
RUN mv target/java-demoapp-${APP_VERSION}.jar target/java-demoapp.jar

#
# Runtime image is just JRE + the fat JAR
#

FROM adoptopenjdk/openjdk16:alpine-jre

WORKDIR /app
COPY --from=build /build/target/java-demoapp.jar .
COPY --from=download /home/curl_user/opentelemetry-javaagent.jar /opentelemetry-javaagent.jar

EXPOSE 8080
ENTRYPOINT ["java","-javaagent:/opentelemetry-javaagent.jar", "-jar", "java-demoapp.jar"]
