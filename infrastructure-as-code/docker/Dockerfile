# task a
# FROM openjdk:17-slim
# COPY spring-petclinic-3.1.0-SNAPSHOT.jar /home/spring-petclinic-3.1.0-SNAPSHOT.jar
# CMD ["java", "-jar", "/home/spring-petclinic-3.1.0-SNAPSHOT.jar"]

# task b
FROM openjdk:17-slim
RUN useradd -ms /bin/bash newuser
USER newuser
WORKDIR /home/newuser
COPY spring-petclinic-3.1.0-SNAPSHOT.jar /home/newuser/spring-petclinic-3.1.0-SNAPSHOT.jar
# CMD ["java", "-jar", "/home/newuser/spring-petclinic-3.1.0-SNAPSHOT.jar"]
# task c
CMD ["java", "-jar", "/home/newuser/spring-petclinic-3.1.0-SNAPSHOT.jar", "-Xms2G", "-Xmx5G"]

