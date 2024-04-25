# Maven ve Java 17 tabanlı bir imaj kullanıyoruz
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

# Projemizin pom.xml dosyasını ve kaynak kodunu kopyalıyoruz
COPY ./pom.xml /app
COPY ./src /app/src

# Projeyi paketliyoruz
RUN mvn clean package -Dmaven.test.skip=true

# Java 17 tabanlı bir imaj kullanıyoruz
FROM openjdk:17-jdk

WORKDIR /app

# Paketlenmiş JAR dosyasını kopyalıyoruz
COPY --from=build /app/target/*.jar app.jar

# Uygulamanın çalışacağı portu belirliyoruz
EXPOSE 8080

# Uygulamamızı çalıştırıyoruz
CMD ["java", "-jar", "app.jar"]