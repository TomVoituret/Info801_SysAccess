# Ajout d'un service REST

Avec Java EE Jakarta EE, il est assez facile de transformer un EJB (ou une classe Java) 
en service Web REST. 
L'ensemble du processus est décrit dans la spécification [Jakarta RESTful Web Services (API JAX-RS)](https://jakarta.ee/specifications/restful-ws/).

Cette spécification définit notamment un ensemble d'API et d'annotations que l'on va pouvoir utiliser pour transformer un EJB (ou un classe Java) en service Web de type REST.  
Tous ces éléments sont regroupés dans le package (et les sous-packages) 

[`javax.ws.rs`](https://jakarta.ee/specifications/restful-ws/2.1/apidocs/) (JEE <= 8) / 
[`jakarta.ws.rs`](https://jakarta.ee/specifications/restful-ws/3.1/apidocs/) (Jakarta EE >= 9).

## Création du service REST

La configuration de service REST peut se faire en ajoutant des annotations sur les classes Java :

- [`jakarta.ws.rs.Path`](https://jakarta.ee/specifications/restful-ws/3.1/apidocs/jakarta.ws.rs/jakarta/ws/rs/path) 
  pour indiquer un chemin (relatif) associé à une ressource REST
- [`jakarta.ws.rs.GET`](https://jakarta.ee/specifications/restful-ws/3.1/apidocs/jakarta.ws.rs/jakarta/ws/rs/get)
  pour indiquer que la ressource va répondre à des requêtes HTTP de type GET
- [`jakarta.ws.rs.POST`](https://jakarta.ee/specifications/restful-ws/3.1/apidocs/jakarta.ws.rs/jakarta/ws/rs/post) 
  pour indiquer que la ressource va répondre à des requêtes HTTP de type POST
- etc.
- [`jakarta.ws.rs.Produces`](https://jakarta.ee/specifications/restful-ws/3.1/apidocs/jakarta.ws.rs/jakarta/ws/rs/produces) 
  pour indiquer le type de données renvoyées par la ressource (JSON / XLM / Texte / ... :
  cf. classe [jakarta.ws.rs.core.MediaType](https://jakarta.ee/specifications/restful-ws/3.1/apidocs/jakarta.ws.rs/jakarta/ws/rs/core/mediatype))
- [`jakarta.ws.rs.Consumes`](https://jakarta.ee/specifications/restful-ws/3.1/apidocs/jakarta.ws.rs/jakarta/ws/rs/consumes) 
  pour indiquer le type de données consomées par la ressource (JSON / XLM / Texte / ... :
  cf. classe [jakarta.ws.rs.core.MediaType](https://jakarta.ee/specifications/restful-ws/3.1/apidocs/jakarta.ws.rs/jakarta/ws/rs/core/mediatype))
- etc.

Dans l'exemple nous avons utilisé une des interfaces de l'EJB, 
[`MesureRest`](../src/main/java/fr/usmb/m2isc/mesure/ejb/MesureRest.java),
pour définir le service REST :

```java
@Path("ticket")
public interface TicketRest {
	@Path("add")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	Mesure addTicket(@QueryParam("piece") String piece, @QueryParam("temp") double val);

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Mesure findTicket(@PathParam("id") long id);
    ...
    ...
}
```

Ainsi la méthode `addTicket` sera associée à une requête de type `POST` et au chemin `ticket/path`
et renverra un objet JSON correspondant au résultat renvoyé par la méthode (la ticket ajoutée).

Les chemins peuvent comprendre des paramètres (éléments entre accolades dans les annotations `@PATH`).

Ces éléments, ainsi que les paramètres de la requête ou les entêtes HTTP ou les cookies 
peuvent être utilisés pour fournir les valeurs des paramètres de méthodes appelées.

Dans le code suivant l'annotation [`PathParam`](https://jakarta.ee/specifications/restful-ws/3.1/apidocs/jakarta.ws.rs/jakarta/ws/rs/pathparam)
permet de définir la valeur du paramètre `id` à partir du paramètre `{id}` défini avec l'annotation `@PATH`.

```java
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Mesure findTicket(@PathParam("id") long id);
``````


## Création de l'application REST

Pour que les services REST qui utilisent les annotations `@Path` soient accessibles, 
il faut aussi créer une [`Application`](https://jakarta.ee/specifications/restful-ws/3.1/apidocs/jakarta.ws.rs/jakarta/ws/rs/core/application)
JAX-RS.

Cela peut se faire en utilisant l'annotation [`jakarta.ws.rs.ApplicationPath`](https://jakarta.ee/specifications/restful-ws/3.1/apidocs/jakarta.ws.rs/jakarta/ws/rs/applicationpath).  
Cette annotation identifie l'application qui servira l'ensenble des ressources fournies avec l'annotation `@Path`.

Dans notre exemple il s'agit de la clase [`AppConf`](../src/main/java/fr/usmb/m2isc/mesure/ejb/AppConf.java) :

```java
@ApplicationPath("/api")
public class AppConf extends Application { }
``````

Le préfixe passé en paramètre à l'annotation `@ApplicationPath` vient s'ajouter au chemins d'accès définis avec `@Path`.  
Du coup si le projet est déployé sur un serveur local, l'url du service REST (pour l'ajout d'un ticket) ressemblera à 
http://localhost:8080/Info801_TP2/api/SysAccess/add?ticketId=24

## Documentation : 

Spécifications **Jakarta RESTful Web Services** :

- Spécifications JAX-RS: https://jakarta.ee/specifications/restful-ws/
- Jakarta RESTful Web Services 2.1 (Jakarta EE 8) : https://jakarta.ee/specifications/restful-ws/2.1/restful-ws-spec-2.1
- Jakarta RESTful Web Services 3.0 (Jakarta EE 9) : https://jakarta.ee/specifications/restful-ws/3.0/jakarta-restful-ws-spec-3.0
- Jakarta RESTful Web Services 3.1 (Jakarta EE 10) : https://jakarta.ee/specifications/restful-ws/3.1/jakarta-restful-ws-spec-3.1

Javadoc :

- Jakarta RESTful Web Services 2.1 (Jakarta EE 8) : https://jakarta.ee/specifications/restful-ws/2.1/apidocs
- Jakarta RESTful Web Services 3.0 (Jakarta EE 9) : https://jakarta.ee/specifications/restful-ws/3.0/apidocs
- Jakarta RESTful Web Services 3.1 (Jakarta EE 10) : https://jakarta.ee/specifications/restful-ws/3.1/apidocs

Tutoriels sur JAX-RS :
- https://eclipse-ee4j.github.io/jakartaee-tutorial/#building-restful-web-services-with-jakarta-rest (Jakarta EE Tutorial)
- https://mbaron.developpez.com/tutoriels/soa/developpement-services-web-jaxrs-maven-eclipse/
- https://gayerie.dev/epsi-b3-javaee/javaee_web/jaxrs.html
- https://www.javaguides.net/2018/09/jax-rs-tutorial.html
- https://www.baeldung.com/jax-rs-spec-and-implementations