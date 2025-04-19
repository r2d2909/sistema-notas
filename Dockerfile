FROM openjdk:17

COPY "./target/ParcialEdissonPedraza-1.jar"  "app.jar"

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8102
 
