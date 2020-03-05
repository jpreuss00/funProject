FROM openjdk:13-alpine
WORKDIR /
ADD build/libs/funProject.jar FunProject.jar
CMD ["java", "-jar", "FunProject.jar", "--port=$PORT"]
