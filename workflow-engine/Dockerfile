# Uncomment in case you want to watch the world burn.

#FROM maven:3.8.1-openjdk-11 AS MAVEN_TOOL_CHAIN
#COPY pom.xml /tmp/
#COPY src /tmp/src/
#WORKDIR /tmp/
#RUN mvn package
#FROM openjdk:11
#EXPOSE 8080
#COPY --from=MAVEN_TOOL_CHAIN /tmp/target/workflow-engine-1.0.0.jar workflow-engine.jar
#ENTRYPOINT ["java","-jar","/camunda-workflow-engine.jar"]

FROM openjdk:11
EXPOSE 8080
COPY target/workflow-engine-1.0.0.jar camunda-workflow-engine.jar
ENTRYPOINT ["java","-jar","/camunda-workflow-engine.jar"]