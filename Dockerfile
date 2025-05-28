#
# Clean package stage
#
FROM maven:3.9.9-amazoncorretto-17-alpine AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Start stage
#
FROM openjdk:17-alpine
COPY --from=build /home/app/target/webapp-0.0.1-SNAPSHOT.jar /usr/local/lib/webapp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/webapp.jar"]