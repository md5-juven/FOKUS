FROM openjdk
WORKDIR usr/lib
ADD ./target/kanbanUser-0.0.1-SNAPSHOT.jar /usr/lib/kanbanUser-0.0.1-SNAPSHOT.jar
ADD ./src/main/resources/welcomeEmail.txt /usr/lib/welcomeEmail.txt
ADD ./src/main/resources/Fokus-05.png /usr/lib/Fokus-05.png
ENTRYPOINT ["java","-jar","kanbanUser-0.0.1-SNAPSHOT.jar"]