FROM jeanblanchard/java:8
VOLUME /tmp
ADD config-microservice-0.1.0.jar app.jar
#For Alpine install bash
RUN apk add --update bash && rm -rf /var/cache/apk/
RUN bash -c 'touch /app.jar'
EXPOSE 8888
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
