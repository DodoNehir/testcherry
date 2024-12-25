FROM eclipse-temurin:17-jdk AS build

WORKDIR /workspace/app

COPY gradlew .
COPY gradle/wrapper gradle/wrapper
COPY build.gradle.kts .
COPY settings.gradle.kts .

# gradle cache
VOLUME gradle_cache:/.gradle

# 의존성만 다운로드
RUN ./gradlew dependencies

COPY src src

RUN ./gradlew build


FROM eclipse-temurin:17-jdk

COPY --from=build /workspace/app/build/libs/*-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]