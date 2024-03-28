FROM quay.io/wildfly/wildfly:27.0.0.Final-jdk17 as base

FROM base as manageable
# Ajout utilisateur admin pour pouvoir utilser la console web
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin#1234 --silent
# mise de la console sur l'ensemble de connections internet (cat par defaut que sur localhost)
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

# build application web
FROM gradle:8.3.0-jdk17 as build
WORKDIR /usr/Thermo
COPY . /usr/Thermo/
RUN gradle build 

# deploiement sur les serveur wildly
FROM manageable as deployed
COPY --from=build /usr/Thermo/build/libs/Thermo.war /opt/jboss/wildfly/standalone/deployments/Thermo.war
