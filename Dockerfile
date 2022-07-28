FROM openjdk:11.0.7-jre-slim
MAINTAINER joserobertofilho.com
EXPOSE 8080
ADD build/libs/personia-0.0.1-SNAPSHOT.jar personia.jar
ENTRYPOINT ["java","-jar","personia.jar"]