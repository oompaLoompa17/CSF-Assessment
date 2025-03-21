# Build Angular
FROM node:23 AS ng-build

WORKDIR /src

RUN npm i -g @angular/cli

# COPY client/public public -> no public folder here
COPY client/src src
COPY client/*.json .

RUN npm ci && ng build

# Build Spring Boot
FROM openjdk:23-jdk AS j-build

WORKDIR /src

COPY server/.mvn .mvn
COPY server/src src
COPY server/mvnw .
COPY server/pom.xml .

# Copy angular files over to static
# for files and folders leave out the *
# for files only include *
COPY --from=ng-build /src/dist/client/browser/ src/main/resources/static/

# RUN chmod a+x mvnw && ./mvnw package -Dmaven.test.skip=true
RUN chmod a+x ./mvnw && ./mvnw package -Dmaven.test.skip=true

# Copy the JAR file over to the final container
FROM openjdk:23-jdk 

WORKDIR /app

COPY --from=j-build /src/target/server-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=3000

ENV SPRING_DATA_MONGODB_URI=

ENV SPRING_DATASOURCE_URL=
ENV SPRING_DATASOURCE_USERNAME=
ENV SPRING_DATASOURCE_PASSWORD=

EXPOSE ${PORT}

SHELL [ "/bin/sh", "-c"]
ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar