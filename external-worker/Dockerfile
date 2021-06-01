# Uncomment in case you want to watch the world burn.

#FROM maven:3.8.1-openjdk-11 AS MAVEN_TOOL_CHAIN
#COPY pom.xml /tmp/
#COPY src /tmp/src/
#WORKDIR /tmp/
#RUN mvn package
#FROM openjdk:11
#EXPOSE 8080
#COPY --from=MAVEN_TOOL_CHAIN /tmp/target/external-worker-1.0.0.jar external-worker.jar
#ENTRYPOINT ["java","-jar","/external-worker.jar"]

FROM openjdk:11
EXPOSE 8080
COPY target/external-worker-1.0.0.jar external-worker.jar
ENTRYPOINT ["java","-jar","/external-worker.jar"]