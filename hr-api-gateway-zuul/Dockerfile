FROM openjdk:11.0.13
VOLUME /tmp
EXPOSE 8765
ADD ./target/hr-api-gateway-zuul-0.0.1-SNAPSHOT.jar hr-gateway-zuul.jar
ENTRYPOINT ["java","-jar","/hr-gateway-zuul.jar"]