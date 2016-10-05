FROM openjdk:8
VOLUME /tmp
ADD target/indexserver.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-Xmx1024m","-jar","/app.jar"]
