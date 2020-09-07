FROM openjdk:11.0.5-jdk as builder
WORKDIR /app
COPY mvnw mvnw
COPY .mvn .mvn
COPY pom.xml pom.xml
COPY common/pom.xml common/pom.xml
COPY common/core/pom.xml common/core/pom.xml
COPY services/pom.xml services/pom.xml
COPY services/add-declaration-service/pom.xml services/add-declaration-service/pom.xml

RUN ./mvnw -B dependency:go-offline
COPY common/core/src common/core/src
COPY services/add-declaration-service/src services/add-declaration-service/src

RUN ./mvnw -B clean package -DskipTestsdoc -Dmaven.test.skip=true --projects :add-declaration-service --also-make clean package

FROM openjdk:11.0.5-jdk
WORKDIR /app
COPY --from=builder /app/services/add-declaration-service/target/add-declaration-service.jar /app/add-declaration-service.jar
ENTRYPOINT ["java","-jar","/app/add-declaration-service.jar"]
