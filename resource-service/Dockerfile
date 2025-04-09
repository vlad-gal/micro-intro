# Build stage
# replaced with gradle:8.5-jdk21 because of segmentation fault due to combination of M1 Apple Silicon, gradle:jdk21-alpine and podman
# A fatal error has been detected by the Java Runtime Environment:
#
#  SIGSEGV (0xb) at pc=0x000000000002b1c0, pid=43, tid=46

FROM gradle:8.5-jdk21 AS build

WORKDIR /build

COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle build.gradle

RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew assemble --no-daemon -x test


# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /build/build/libs/*.jar resource-service.jar

EXPOSE 8080

CMD ["java", "-jar", "resource-service.jar", "com.epam.resource.ResourceApp"]
