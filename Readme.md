# TP Info706 Parking #
Voituret Tom
Enzo Marchal

Initialisation du projet à partir du projet Thermo-master







# Info706 – TP JEE (JavaEE/JakartaEE) #

On souhaite simuler une application de gestion d'un parking.

Le parking comporte 3 bornes (qui seront simulées à l'aide de pages WEB).

1 borne d'entrée qui distribue des tickets,
1 borne de paiement qui permet de payer avant de sortir et d'éditer des justificatifs,
1 borne de sortie.

Les tickets sont représentés sous la forme d'entités JPA conservés dans 
la base de données afin de pouvoir être exploités ultérieurement

Un ticket comport 3 champs :

un identifiant unique (un id)
une date d'entrée,
une date de sortie.

Pour chaque ticket, on gard également en base de données la liste des paiment effectués.
Pour chaque paiement on conserve :

un identifiant du paiement (id),
la date du paiement,
le montant payé (en euro (un float)),
le type de paiement utilisé (CB, espèces).


Fonctionnement des bornes
La première borne sert juste à créer un ticket.
La deuxième sert à payer avant de sortir :
elle attent l'insertion d'un ticket, calcule le prix à payer (2 centimes d'euro la minute, par exemple)
et permet à l'utilisateur de payer (ou non).
Elle permet également d'éditer, à la demande du client, un justificatif indiquant :

le numero du ticket,
la date d'entrée,
la date du dernier paiement,
le montant total payé par le client (somme des différents paiements effectués).

La troisième borne permet de valider la sortie de l'utilisateur :
après avoir payé l'utilisateur disposera en effet d'un temps limité pour sortir (typiquement un quart d'heure).
Si l'utilisateur n'est pas sorti à temps, il devra alors retourner payer le complément à la borne de paiement
avant de pouvoir revenir à la borne de sortie.
Remarque : afin de pouvoir plus facilement tester l'application,
on simule un ecoulement du temps accéléré, ici 2 minutes.

Les tickets et les paiements sont stockées dans une base de données. On y accède à travers des entités (JPA).

des EJBs session sans état (stateless) servent de façade pour les opérations de création/recherche des tickets et des paiement.

Un client WEB permet de réaliser l'ensemble des opérations : création d'un ticket, affichage des tickets, paiement ect.
Ce client comporte sept pages JSP et plusieurs servlet (1 par opération). La connexion au bean session se fait en local dans les servlet sans passer par une interface.

## Organisation du projet ##

### Partie JPA ###

Il y a 2 objets persistants (stockage des tickets et des paiements) defini à l'aide des classes `Ticket` et `Payment` :  
- <a href="src/main/java/fr/usmb/m1_706/parking/jpa/Ticket.java" >src/main/java/fr/usmb/m1_706/parking/jpa/Ticket.java</a> (implantation de l'entité _Ticket_ (entité JPA))
- <a href="src/main/java/fr/usmb/m1_706/parking/jpa/Payment.java" >src/main/java/fr/usmb/m1_706/parking/jpa/Payment.java</a> (implantation de l'entité _Payment_ (entité JPA))
- <a href="src/main/resources/META-INF/persistence.xml" >META-INF/persistence.xml</a> (descripteur standard JPA)

### Partie EBJ ###

L'accès aux tickets et au paiements se fait au travers de 2 EJBs (Enterprise JavaBean) sans état (et utilisable sans interface - cf. annotation `@LocalBean`) :  
- <a href="src/main/java/fr/usmb/m1_706/parking/ejb/TicketEJB.java" >src/main/java/fr/usmb/m1_706/parking/ejb/TicketEJB.java</a> EJB des tickets sans état (Stateless)
- <a href="src/main/java/fr/usmb/m1_706/parking/ejb/PaymentEJB.java" >src/main/java/fr/usmb/m1_706/parking/ejb/PaymentEJB.java</a> EJB des paiements sans état (Stateless)

### Partie WEB  ###

Différentes servlet permettent permettent d'executer les actions (création de tickets, calcule du montant à payer (par numéro de ticket (id)), sortie du parking, ect) :
- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/CreateTicketServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/CreateTicketServlet.java</a> (Créer un ticket)
- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/CalculeMontantServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/CalculeMontantServlet.java</a> (calcule du prix à payer pour un ticket)
- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/PaymentServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/PaymentServlet.java</a> (Créer un paiement si l'utilisateur paie le prix affiché)
- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/JustificatifServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/JustificatifServlet.java</a> (Permet de créer un justificatif pour l'utilisateur)
- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/GoToExitValidationServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/GoToExitValidationServlet.java</a> (se rendre sur la borne de sortie)
- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/ExitValidationServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/ExitValidationServlet.java</a> (Permet à l'utilisateur de sortir du parking si ticket et paiement valide)
- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/ShowAllTicketsServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/ShowAllTicketsServlet.java</a> (Montre tous les tickets créent)
- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/ShowAllPaymentsServlet.java"> src/main/java/fr/usmb/m1_706/parking/servlet/ShowAllPaymentsServlet.java</a> (Montre tous les paiements créent)
- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/ShowLastTicketServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/ShowLastTicketServlet.java</a> (Montre le dernier ticket crée)
- src/main/webapp/WEB-INF/web.xml (descripteur standard de l'application Web -- optionnel dans les dernières versions de JavaEE)

L'affichage des mesures se fait à la fin des servlet en redirigeant les requêtes vers des pages JSP (JavaServer Pages) :  
- <a href="src/main/webapp/CreateTicket.jsp" >src/main/webapp/CreateTicket.jsp</a> (permet de créer un ticket)
- <a href="src/main/webapp/Payment.jsp" >src/main/webapp/Payment.jsp</a> (Permet d'insérer un tickets, payer le prix et créer un justificatif)
- <a href="src/main/webapp/ExitValidation.jsp" >src/main/webapp/ExitValidation.jsp</a> (borne permettant de rentrer votre numéro de ticket pour sortir)
- <a href="src/main/webapp/showTicket.jsp" >src/main/webapp/showTicket.jsp</a> (montre tous les tickets existants)
- <a href="src/main/webapp/showPayment.jsp" >src/main/webapp/showPayment.jsp</a> (montre tous les paiements existants)
- <a href="src/main/webapp/showLastTicket.jsp" >src/main/webapp/showLastTicket.jsp</a> (montre le dernier ticket crée)
- <a href="src/main/webapp/index.jsp" >src/main/webapp/index.jsp</a> (page d'accueil permettant d'acceder au autres pages)


## Fonctionnement ##

### Manipulation des objets persistants dans l'EJB ###

Toutes les manipulations sur les objets persistants se font dans les EJBs en utilisant l'__entity manager__ correspondant à l'__unité de persistance__ des objets persistants manipulés. 

Dans l'EJB on utilise l'annotation `@PersistenceContext` pour récupérer auprès du serveur JavaEE l'_entity manager_ désiré.  
####(exemple class TicketEJB)####

```java 
@Stateless
@LocalBean
@Local
public class TicketEJB implements TicketRest{

	@PersistenceContext(unitName = "ParkingPU")
    	private EntityManager em;

```

L'_entity manager_ est ensuite utilisé dans les méthodes de l'EJB pour ajouter des tickets :  

```java
	public Ticket createTicket() {
	        Ticket ticket = new Ticket();
	        em.persist(ticket);
	        return ticket;
    }
```

où retrouver des tickets dans la base :  

```java
	public Ticket findTicket(long ticketId) {
	        return em.find(Ticket.class, ticketId);
    }
```
```java
	public List<Ticket> getAllTickets() {
	        TypedQuery<Ticket> query = em.createQuery("SELECT t FROM Ticket t", Ticket.class);
	        return query.getResultList();
    }
```

### Utilisation de l'EJB dans les servlet ###

Dans les _servlet_ on utilise l'annotation `@EJB` pour obtenir une référence de l'_EJB session_ :

```java
@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private PaymentEJB paymentEJB;

    @EJB
    private TicketEJB ticketEJB;
```

Pour l'affichage, dans les servlet, on ajoute dans la requete http les objets java à afficher et on redirige les requêtes vers les pages JSP :  

```java
	// Appel de la méthode createPayment du PaymentEJB pour créer un nouveau paiement
	paymentEJB.createPayment(ticketId, amount, paymentType);
	        
	// Mise en place des attributs de requête pour la transmission d'informations à la vue
	request.setAttribute("ticketId", ticketId);
	request.setAttribute("recap", true);
	        
	// Redirection vers la page Payment.jsp
	request.getRequestDispatcher("/Payment.jsp").forward(request, response);
}
```


## Packaging ##

Comme c'est une application ciblant le profil web de la spécification JavaEE 8, tout (Servlet/JSP/EJB/entité JPA) peut être empaqueté dans la même archive web (Info706_Parking.war).

### L'application WEB est entièrement packagée dans un fichier d'archive war ###

Les resources web (pages html, JSP, feuilles de styles CSS, etc. sont ajoutées à la racine du fichier d'archive.

Deux dossiers dossiers spécifiques, `META-INF` et `WEB-INF` dans l'archive war permettent de configurer l'application web et gérer les parties en java.
<pre>
Info706_Parking.war
  |-- <a href="src/main/webapp/index.jsp" >src/main/webapp/index.jsp</a> (page d'accueil permettant d'acceder au autres pages)
  |-- <a href="src/main/webapp/CreateTicket.jsp" >src/main/webapp/CreateTicket.jsp</a> (permet de créer un ticket)
  |-- <a href="src/main/webapp/Payment.jsp" >src/main/webapp/Payment.jsp</a> (Permet d'insérer un tickets, payer le prix et créer un justificatif)
  |-- <a href="src/main/webapp/ExitValidation.jsp" >src/main/webapp/ExitValidation.jsp</a> (borne permettant de rentrer votre numéro de ticket pour sortir)
  |-- <a href="src/main/webapp/showTicket.jsp" >src/main/webapp/showTicket.jsp</a> (montre tous les tickets existants)
  |-- <a href="src/main/webapp/showPayment.jsp" >src/main/webapp/showPayment.jsp</a> (montre tous les paiements existants)
  |-- <a href="src/main/webapp/showLastTicket.jsp" >src/main/webapp/showLastTicket.jsp</a> (montre le dernier ticket crée)
  |-- <a href="src/main/webapp/default.css" >default.css</a> (feuille de style css)
  |-- <a href="src/main/webapp/META-INF/MANIFEST.MF" >META-INF/MANIFEST.MF</a> (java manifeste)
  |-- WEB-INF/web.xml (descripteur standard de l'application Web -- optionnel dans les dernières versions de JavaEE)
  |-- WEB-INF/lib (librairies supplémentaires java utilisées par les classes java)
  |-- WEB-INF/classes (classes java : servlet / EJB / entités JPA)
                |-- <a href="src/main/resources/META-INF/persistence.xml" >META-INF/persistence.xml</a> (descripteur standard JPA)
                |-- META-INF/orm.xml (descripteur optionnel pour le mapping objet-relationnel -- absent ici)
                |-- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/CreateTicketServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/CreateTicketServlet.java</a> (Créer un ticket)
                |-- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/CalculeMontantServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/CalculeMontantServlet.java</a> (calcule du prix à payer pour un ticket)
                |-- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/PaymentServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/PaymentServlet.java</a> (Créer un paiement si l'utilisateur paie le prix affiché)
                |-- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/JustificatifServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/JustificatifServlet.java</a> (Permet de créer un justificatif pour l'utilisateur)
		|-- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/GoToExitValidationServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/GoToExitValidationServlet.java</a> (se rendre sur la borne de sortie)
                |-- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/ExitValidationServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/ExitValidationServlet.java</a> (Permet à l'utilisateur de sortir du parking si ticket et paiement valide)
                |-- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/ShowAllTicketsServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/ShowAllTicketsServlet.java</a> (Montre tous les tickets créent)
                |-- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/ShowAllPaymentsServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/ShowAllPaymentsServlet.java</a> (Montre tous les paiements créent)
                |-- <a href="src/main/java/fr/usmb/m1_706/parking/servlet/ShowLastTicketServlet.java" >src/main/java/fr/usmb/m1_706/parking/servlet/ShowLastTicketServlet.java</a> (Montre le dernier ticket crée)
                |-- <a href="src/main/java/fr/usmb/m1_706/parking/ejb/TicketEJB.java" >src/main/java/fr/usmb/m1_706/parking/ejb/TicketEJB.java</a> (EJB des tickets sans état)
		|-- <a href="src/main/java/fr/usmb/m1_706/parking/ejb/PaymentEJB.java" >src/main/java/fr/usmb/m1_706/parking/ejb/PaymentEJB.java</a> (EJB des paiements sans état)
                |-- <a href="src/main/java/fr/usmb/m1_706/parking/jpa/Ticket.java" >src/main/java/fr/usmb/m1_706/parking/jpa/Ticket.java</a> (entité Ticket (entité JPA))
		|-- <a href="src/main/java/fr/usmb/m1_706/parking/jpa/Payment.java" >src/main/java/fr/usmb/m1_706/parking/jpa/Payment.java</a> (entité Payment (entité JPA))
</pre>

### Utilisation d'une base de données externe

[Utilisation d'une base de données PostgreSQL](docs/Extarnal-DB.md)

## Usage : ##

Pour voir les sources, il suffit de cloner le projet git et de l'importer (sous forme de projet gradle) dans votre IDE favori. 
Cela devrait permettre la création d'un projet (ou module) web.

La compilation des classes et la création de l'archive war peut se faire via gradle en appelant la tâche `build` sur le projet principal.

### Utilisation avec un serveur Jakarta EE local ###

Pour utiliser l'exemple il suffit de déployer le fichier Info706_Parking.war_ sur un serveur JavaEE 8. 
Le client Web est alors déployé avec le préfixe _/Info706_Parking_. 
Il devrait donc être accessible à l'adresse http://localhost:8080/Info706_Parking/index.jsp

## Postgres ##

on a ici pour la base de données postgres avec un utilisateur:"postgres", un mot de passe:"admin" et une base de données:"parking" (crée avec pgAdmin 4 sous windows).

## 1 version ##
- la branche [feature/db-externe](../../tree/feature/db-externe) utilise une base de données externe [PostgreSQL](https://www.postgresql.org/) 
  à la place de la base [H2](https://www.h2database.com/html/main.html) par défaut de *WildFly*.

## Documentation : ##

Java EE 7 (Oracle)
- Doc : http://docs.oracle.com/javaee/7
- Tutoriel : https://docs.oracle.com/javaee/7/tutorial
- API (javadoc) : http://docs.oracle.com/javaee/7/api
- Spécifications : https://www.oracle.com/java/technologies/javaee/javaeetechnologies.html#javaee7

Jave EE 8 (Oracle)
- Doc : https://javaee.github.io/glassfish/documentation
- Tutoriel : https://javaee.github.io/tutorial/
- API (javadoc) : https://javaee.github.io/javaee-spec/javadocs/
- Spécifications : https://www.oracle.com/java/technologies/javaee/javaeetechnologies.html#javaee8
- Serveurs compatibles : https://www.oracle.com/java/technologies/compatibility-jsp.html

Jakarta EE 8 (Fondation Eclipse)
- Doc : https://javaee.github.io/glassfish/documentation
- Tutoriel : https://javaee.github.io/tutorial/
- API (javadoc) : https://jakarta.ee/specifications/platform/8/apidocs/
- Spécifications : https://jakarta.ee/specifications
- Serveurs compatibles : https://jakarta.ee/compatibility/certification/8/

Jakarta EE 9 (Fondation Eclipse)
- Doc : https://jakarta.ee/resources/#documentation
- Tutoriel : https://eclipse-ee4j.github.io/jakartaee-tutorial/
- API (javadoc) : https://jakarta.ee/specifications/platform/9/apidocs/
- Spécifications : https://jakarta.ee/specifications
- Serveurs compatibles : 
    - https://jakarta.ee/compatibility/certification/9/
    - https://jakarta.ee/compatibility/certification/9.1/

Jakarta EE 10 (Fondation Eclipse)
- Doc : https://jakarta.ee/resources/#documentation
- API (javadoc) : https://jakarta.ee/specifications/platform/10/apidocs/
- Spécifications : https://jakarta.ee/specifications
- Serveurs compatibles : 
    - https://jakarta.ee/compatibility/certification/10/
