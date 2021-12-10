FROM maven:latest AS build 
WORKDIR root

COPY ./src/main ./src/main
COPY ./pom.xml  ./
RUN mvn install
RUN mvn package

FROM quay.io/keycloak/keycloak:latest

COPY --from=build /root/src/main/resources/themes/mycompany/ /opt/jboss/keycloak/themes/mycompany
COPY --from=build  /root/target/CustomUserProvider-0.0.1-SNAPSHOT.jar /opt/jboss/keycloak/standalone/deployments

