FROM java:8-jre
WORKDIR usr/src
ENV MYSQL_DATABASE=newsdb
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_CI_URL=jdbc:mysql://localhost:3306/newsdb
ADD ./target/Server-0.0.1-SNAPSHOT.jar /usr/src/Server-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","Server-0.0.1-SNAPSHOT.jar"]

