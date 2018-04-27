FROM maven:3.5.3-jdk-8-alpine
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package
WORKDIR /usr/src/app/target
CMD [ "java", "-jar", "chat-message-service.jar" ]