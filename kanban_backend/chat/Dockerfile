FROM openjdk
WORKDIR usr/lib
ADD ./target/chat-0.0.1-SNAPSHOT.jar /usr/lib/chat-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","chat-0.0.1-SNAPSHOT.jar"]