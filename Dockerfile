FROM openjdk:17-alpine
RUN apk add --no-cache curl
ARG JAR_FILE=target/*.jar
EXPOSE 8080
RUN chmod +x mvnw
RUN ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install
COPY ${JAR_FILE} testtask-webapp.jar
ENTRYPOINT ["java", "-jar", "testtask-webapp.jar"]