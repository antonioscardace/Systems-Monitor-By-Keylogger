FROM maven:3-amazoncorretto-21-debian
LABEL maintainer="Antonio Scardace"

WORKDIR /usr/src/app/
COPY . .

EXPOSE 8888

RUN mvn dependency:go-offline
RUN mvn clean package

CMD [ "sh", "-c", "mvn spring-boot:run" ]