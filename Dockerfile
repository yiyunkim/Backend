FROM ubuntu:22.04

# 기본 도구와 Java 17 설치 & 캐시 정리
RUN apt-get update && \
    apt-get install -y \
    openjdk-17-jdk \
    findutils \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /Backend
COPY . .
RUN chmod +x ./gradlew
# 빌드 (테스트 제외)
# RUN ./gradlew build -x test
# 빌드 (build 대신 bootJar 사용)
RUN ./gradlew bootJar
# 빌드 결과물 .jar 찾기 및 찾은 각 파일에 대한 상세정보 표시
RUN find build/libs -name "*.jar" -type f -exec ls -l {} \;

EXPOSE 8080
CMD ["java", "-jar", "-Dspring.profiles.active=local", "build/libs/yiyunkim-0.0.1-SNAPSHOT.jar"]