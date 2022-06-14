FROM openjdk:11
EXPOSE 8080
RUN mkdir -p /app/
ADD build/libs/delta_currency_checker-0.0.1-SNAPSHOT.jar /app/delta_currency_checker-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/app/delta_currency_checker-0.0.1.jar"]