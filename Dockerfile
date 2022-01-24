FROM openjdk:11-jre-slim
COPY build/libs/Url-Shortener.jar Url-Shortener.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/Url-Shortener.jar"]
