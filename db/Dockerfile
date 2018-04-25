FROM maven:3.5.3-jdk-8-alpine
COPY ~/.m2/repository/com/oracle/jdbc/ojdbc8/12.2.0.1 /usr/share/maven/ref/repository/com/oracle/jdbc/ojdbc8/12.2.0.1
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package
WORKDIR /usr/src/app/target
CMD [ "java", "-jar", "db.jar" ]