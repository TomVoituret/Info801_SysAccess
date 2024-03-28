# Ajout d'un service Web SOAP

Avec Java EE Jakarta EE, il est assez facile de transformer un EJB en service Web 
[SOAP](https://fr.wikipedia.org/wiki/SOAP). 
L'ensemble du processus est décrit dans les spécifications :

- [Jakarta XML Web Services](https://jakarta.ee/specifications/restful-ws/)
- [Jakarta XML Binding](https://jakarta.ee/specifications/xml-binding/)

Cette spécification définit notamment un ensemble d'API et d'annotations que l'on va pouvoir utiliser pour transformer un EJB session sans état (ou un classe Java) en service Web de type SOAP.  
Tous ces éléments sont regroupés dans les package (et les sous-packages) suivants :

JEE/jakarta EE <= 8 :

- [javax.jws](https://jakarta.ee/specifications/web-services-metadata/2.1/apidocs/) - Jakarta Web Services Metadata
- [javax.xml.ws](https://jakarta.ee/specifications/xml-web-services/2.3/apidocs/) - Jakarta XML Web Services
- [jakarta.xml.bind](https://jakarta.ee/specifications/xml-binding/2.3/apidocs/) - Jakarta XML Binding

Jakarta EE >= 9 :

- [javax.jws et javax.xml.ws](https://jakarta.ee/specifications/xml-web-services/4.0/apidocs/) - Jakarta XML Web Services
- [jakarta.xml.bind](https://jakarta.ee/specifications/xml-binding/4.0/apidocs/) - Jakarta XML Binding


## Création du service SOAP

La configuration de service REST peut se faire en ajoutant des annotations sur un EJB Session sans état :

- [`jakarta.jws.WebService`](https://jakarta.ee/specifications/xml-web-services/4.0/apidocs/jakarta.xml.ws/jakarta/jws/webservice) 
  pour indiquer qu'une classe ou une interface va correspondre à us service Web SOAP
- [`jakarta.jws.WebMethod`](https://jakarta.ee/specifications/xml-web-services/4.0/apidocs/jakarta.xml.ws/jakarta/jws/webmethod)
  pour préciser la façon dont une méthode sera exposée dans le service Web SOAP
- [`jakarta.jws.WebParam`](https://jakarta.ee/specifications/xml-web-services/4.0/apidocs/jakarta.xml.ws/jakarta/jws/webparam) 
  pour préciser la mapping entre un paramètre d'une méthode individuel sont équivalent XML
- [`jakarta.jws.WebResult`](https://jakarta.ee/specifications/xml-web-services/4.0/apidocs/jakarta.xml.ws/jakarta/jws/webresult) 
  pour préciser la mapping entre le résultat renvoyé par une méthode individuel sont équivalent XML
- etc.

Dans la pratique, si on ne veut pas customiser outre mesure le service Web SOAP, 
il suffit d'ajouter un annotation `@WebService` à un EJB Session sans état pour le le Serveur JEE
fournisse que le service Web SOAP correspondant soit mis en place (toutes les méthodes publiques de l'EJB sont ajoutées par défaut au service SOAP).

```java
@WebService
@Stateless
@LocalBean
@Local
public class TicketEJB implements TicketRest {
	@PersistenceContext
	private EntityManager em;
    ...
    ...
}
```

Le serveur JEE crée le service SOAP et le rend disponible, ainsi que sa description 
[WSDL](https://fr.wikipedia.org/wiki/Web_Services_Description_Language), 
en utilisant le nom de l'EJB.

Dans notre cas le service Soap sera donc accessible à l'adresse : http://localhost:8080/Thermo/MesureEJB
et son `WSDL` à l'adresse : http://localhost:8080/Thermo/MesureEJB?wsdl

## Remarques importantes

Les API SOAP (spécifications *Jakarta XML Web Services*, *Jakarta XML Binding*, *Jakarta SOAP with Attachments*, etc.) 
ne font pas partie du *Profil Web* ([`Jakarta EE Web Profile`](https://jakarta.ee/specifications/webprofile/10/jakarta-webprofile-spec-10.0#web-profile-definition)).

Un serveur JEE implantant juste la spécification `Jakarta EE Web Profile` 
ne sera donc généralement pas suffisant pour implanter des *services Web SOAP*,
un serveur complet (implantant la spécification [`Jakarta EE Platform`](https://jakarta.ee/specifications/platform/))
sera donc généralement plus approprié.

Par ailleurs les spécifications [`Jakarta EE Platform 9 et 10`](https://jakarta.ee/specifications/platform/10/jakarta-platform-spec-10.0#a3252) 
ont rendu optionnel le support par les serveurs Jakarta EE d'un certains nombres de spécifications liées à SOAP :

- [`Jakarta Enterprise Web Services`](https://jakarta.ee/specifications/enterprise-ws/2.0/enterprise-ws-spec-2.0)
- [`Jakarta XML Web Services`](https://jakarta.ee/specifications/xml-web-services/4.0/jakarta-xml-ws-spec-4.0)
- [`Jakarta XML Binding`](https://jakarta.ee/specifications/xml-binding/4.0/jakarta-xml-binding-spec-4.0)
- [`Jakarta Web Services Metadata`](https://jakarta.ee/specifications/web-services-metadata/3.0/ws-metadata-spec-3.0)
- [`Jakarta SOAP with Attachments`](https://jakarta.ee/specifications/soap-attachments/3.0/jakarta-soap-spec-3.0)

Il est donc utile de vérifier si votre serveur Jakarta EE 10 inclut ou non en standard le support des services Web SOAP 
(c'est le cas, par exemple, de Wildfly et de Payara) et, dans le cas contraire, de regarder comment faire pour ajouter et configurer les composants nécessaires à ce support.

## Tutoriels: 

- https://eclipse-ee4j.github.io/jakartaee-tutorial/#building-web-services-with-jakarta-xml-web-services (Jakarta EE Tutorial)
- https://www.jmdoudoux.fr/java/dej/chap-service-web.htm
- https://tahe.developpez.com/tutoriels-cours/webservice-soap/?page=page_5
- https://gayerie.dev/udev-javaee/javaee_web/java_ws.html
