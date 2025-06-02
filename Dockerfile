# Базовий образ із JDK і Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Робоча директорія
WORKDIR /app

# Копіюємо всі необхідні файли для збірки
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

# Копіюємо весь код і збираємо проєкт
COPY . .
RUN ./mvnw package -DskipTests

# -------- Runtime --------

# Мінімальний образ для запуску
FROM eclipse-temurin:17-jdk-alpine

# Робоча директорія
WORKDIR /app

# Копіюємо зібраний jar з попереднього етапу
COPY --from=build /app/target/*.jar app.jar

# Вказуємо порт
EXPOSE 8080

# Команда запуску
CMD ["java", "-jar", "app.jar"]
