# Start with a base image containing Java runtime
FROM adoptopenjdk:15-jre-hotspot

# Add Author info
MAINTAINER Ahn Yeri <dpfl1090@naver.com>

COPY build/libs/demo-*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]