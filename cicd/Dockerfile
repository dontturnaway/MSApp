FROM openjdk:17
WORKDIR /app
COPY build/libs/*.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "myapp.jar"]
#BUILD IMAGE USING FOLLOWING CMD
#With localrepo: "Docker build . -t myapp:v1.0.0"
#With Dockerhub: "Docker build . -t deem0ne/myapp:latest"
#CREATE CONTAINER
#docker run -p 8080:8080 --name myappdbctr --net myappnet myappdb
#CREATE COMPOSITION
#docker-compose -f /Users/Rage/Desktop/myapp.yaml up
#UPLOAD IMAGE
#docker push deem0ne/myapp:latest