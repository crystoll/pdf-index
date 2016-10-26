FROM openjdk:8
ENV PDF_FOLDER ./pdf-files 
VOLUME /tmp
ADD target/indexserver-fat.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Xmx512m","-jar","/app.jar"]
