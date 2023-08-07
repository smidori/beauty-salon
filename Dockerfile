FROM openjdk:17
ADD /target/beauty-salon-0.0.1-SNAPSHOT.jar beauty-salon-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","beauty-salon-0.0.1-SNAPSHOT.jar"]