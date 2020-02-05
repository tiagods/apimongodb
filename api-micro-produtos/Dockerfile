FROM java:8
VOLUME /tmp
ADD target/*.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo/products", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
