# Utilisation d'une base de données externe

Par défaut, si dans le fichier `persistence.xml` aucune source données n'est indiquée, 
le serveur JEE/Jakarta EE doit normalement utiliser la base de données par défaut du serveur.

Dans le cas de `Wilfly` il s'agit d'une base [H2](https://www.h2database.com/html/main.html) 
[sans stockage persistent](https://www.h2database.com/html/features.html#in_memory_databases) 
(la base est réinitialisée à chaque redémarrage du serveur).

## Ajout d'une source de données sur le serveur Jakarta EE

Dans le cas d'un serveur de production, ce n'est généralement pas ce que l'on souhaite avoir. 
Sur un serveur de production, normalement on préconfigurerait le serveur pour une connexion 
vers le base de données souhaitée. 

Cela implique d'avoir un base de donnée externe disponible, d'ajouter le 
[driver JDBC](https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/java/sql/Driver.html) 
de la base de données dans le serveur Jakarta EE, 
de configurer une ou plusieurs [DataSource](https://docs.oracle.com/en/java/javase/17/docs/api/java.sql/javax/sql/CommonDataSource.html)
pour cette base et de les associer à des identifiants 
[JNDI](https://docs.oracle.com/en/java/javase/17/docs/api/java.naming/module-summary.html).

La façon de configurer une source de données sur un serveur Jakarta EE varie en fonction du serveur :

- Wildfly Docs : https://docs.wildfly.org/
- Configuration des sources de données pour Wildly : https://docs.wildfly.org/29/Admin_Guide.html#DataSource
- Payara community docs : https://docs.payara.fish/community/docs/Overview.html
- Configuration des sources de données pour Payara : https://docs.payara.fish/community/docs/Technical%20Documentation/Payara%20Server%20Documentation/Server%20Configuration%20And%20Management/JDBC%20Resource%20Management/JDBC.html

## Configuration d'une source de données avec le déploiement d'une application

Dans le cas où on ne souhaite pas modifier le serveur Jakarta EE, il est également possible de créer 
la source de données (associée au serveur de base de données externe) 
en même temps que l'on déploie une application Jakarta EE.

Cela implique 

- d'ajouter le `driver JDBC` de notre base de données dans les librairies de l'application
- de configurer la source de données associée au moyen de l'annotation 
  [`DataSourceDefinition`](https://jakarta.ee/specifications/platform/10/apidocs/jakarta/annotation/sql/datasourcedefinition)
  ou des descripteurs xml de l'application
- de configurer l'unité de persistance pour utiliser cette `DataSource` (fichier [persistence.xml](../src/main/resources/META-INF/persistence.xml))

### Ajout du driver JDBC dans les librairies de l'application

Dans notre cas nous allons utiliser une base [PostgreSQL](https://www.postgresql.org/about/),
nous allons donc ajouter le driver JDBC de PostgreSQL à l'application.  
Pour ce faire il suffit d'ajouter *jar* du driver [`postgresql-42.6.0.jar`](https://github.com/pgjdbc/pgjdbc/releases)
dans le dossier `WEB-INF/lib` (tous les jars de ce dossiers sont ajoutés au classpath de l'application Web lors du déploiement).

### Configuration de la DataSource

Dans la classe [`MesureEJB`](../src/main/java/fr/usmb/m2isc/mesure/ejb/MesureEJB.java)
on utilise l'annotation [`DataSourceDefinition`](https://jakarta.ee/specifications/platform/10/apidocs/jakarta/annotation/sql/datasourcedefinition)
pour définir et configurer notre `DataSource`.

```java
@DataSourceDefinition(
        name = "java:app/env/jdbc/MyDataSource",
        className = "org.postgresql.ds.PGPoolingDataSource",
        user = "postgres",  // Mettez � jour avec le nom d'utilisateur correct
        password = "admin",  // Mettez � jour avec le mot de passe correct
        serverName = "localhost",
        portNumber = 5432,
        databaseName = "parking")
``````
On y définit le nom JNDI de notre source de données `java:app/env/jdbc/MyDataSource` et 
toutes les informations permettant d'accéder à la base de données...

### Configuration JPA

Il s'agit ici de modifier fichier [persistence.xml](../src/main/resources/META-INF/persistence.xml)
afin d'associer notre source de données à notre unité de persistence JPA.

Pour ce faire on va pouvoir utiliser le tag `<jta-data-source>` (et/ou le tag `<non-jta-data-source>`)
qui va indiquer le nom JNDI de la `DataSource` à utiliser : `java:app/env/jdbc/MyDataSource` dans notre cas.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
  xmlns="http://xmlns.jcp.org/xml/ns/persistence"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">

    <!-- Persistence unit de la bdd "Parking" -->
    <persistence-unit name="ParkingPU">
        <jta-data-source>java:app/env/jdbc/MyDataSource</jta-data-source>
        <exclude-unlisted-classes>false</exclude-unlisted-classes>
        <properties>
            <property name="jakarta.persistence.schema-generation.database.action" value="create" />
            <property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/parking"/>
            <property name="javax.persistence.jdbc.user" value="postgres"/>
            <property name="javax.persistence.jdbc.password" value="admin"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
        </properties>
    </persistence-unit>
</persistence>
```

**Remarque :** 

La configuration donnée ici correspond à la configuration docker 
définie dans le fichier `../compose.yml`, avec un conteneur pour le serveur Wilfly et 
un conteneur pour la base de PostgreSQL.  
Si vous l'utilisez avec un serveur local, pensez à changer le nom du serveur dans l'annotation `@DataSourceDefinition`.

Utiliser la commande `docker compose up -d --build`, pour contruire l'application web et
démarrer les deux serveurs.

