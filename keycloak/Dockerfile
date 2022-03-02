FROM maven:latest AS build 
WORKDIR root

COPY ./src/main ./src/main
COPY ./pom.xml  ./
RUN mvn install
RUN mvn package 

FROM quay.io/keycloak/keycloak:15.0.2

COPY --from=build /root/src/main/resources/themes/mydomain/ /opt/jboss/keycloak/themes/mydomain
COPY --from=build  /root/target/FederatedUserStorage-0.0.1-SNAPSHOT.jar /opt/jboss/keycloak/standalone/deployments

