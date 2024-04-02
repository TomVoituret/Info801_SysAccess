package fr.usmb.m1_801.SysAccess.ejb;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Local;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.jws.WebService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import fr.usmb.m1_801.SysAccess.jpa.*;

@DataSourceDefinition(
        name = "java:app/env/jdbc/MyDataSource",
        className = "org.postgresql.ds.PGPoolingDataSource",
        user = "postgres",  // Mettez à jour avec le nom d'utilisateur correct
        password = "admin",  // Mettez à jour avec le mot de passe correct
        serverName = "localhost",
        portNumber = 5432,
        databaseName = "SysAccess")


@WebService
@Stateless
@LocalBean
@Local
public class UtilisateurEJB implements UtilisateurRest {

    @PersistenceContext(unitName = "SysAccessPU")
    private EntityManager em;

    /**
     * Méthode pour ajouter un nouvel utilisateur à la base de données.
     *
     * @param nom    Le nom de l'utilisateur.
     * @param prenom Le prénom de l'utilisateur.
     * @param type   Le type de l'utilisateur.
     * @return L'utilisateur ajouté avec son ID généré.
     */
    @Override
    public Utilisateur addUtilisateur(String nom, String prenom, String type) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setType(type);
        
        em.persist(utilisateur);
        return utilisateur;
    }
    
    /**
     * Méthode pour récupérer tous les utilisateurs depuis la base de données.
     *
     * @return La liste de tous les utilisateurs.
     */
    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class);
        return query.getResultList();
    }
    @Override
    public Utilisateur deleteUtilisateur(Long id) {
        // Récupérer l'utilisateur à supprimer
        Utilisateur utilisateurASupprimer = em.find(Utilisateur.class, id);
        
        // Vérifier si l'utilisateur existe
        if (utilisateurASupprimer != null) {
            // Si l'utilisateur existe, le supprimer
        	em.remove(utilisateurASupprimer);
            return utilisateurASupprimer;
        } else {
            // Si l'utilisateur n'existe pas, retourner null ou lancer une exception
            return null;
        }
    }
    
 // Méthode pour récupérer les IDs des utilisateurs actuellement dans un bâtiment spécifique
    @Override
    public List<Long> getUtilisateurIdsInBat(Long idBat) {
        TypedQuery<Long> query = em.createQuery("SELECT DISTINCT t.utilisateur.id FROM JournalDeBord t WHERE t.heureSortie IS NULL AND t.batiment.id = :idBat", Long.class);
        query.setParameter("idBat", idBat);
        return query.getResultList();
    }

    @Override
    public List<Long> getUtilisateurIdsInAllBat() {
        TypedQuery<Long> query = em.createQuery("SELECT DISTINCT t.utilisateur.id FROM JournalDeBord t WHERE t.heureSortie IS NULL", Long.class);
        return query.getResultList();
    }

    // Méthode pour récupérer les utilisateurs correspondant aux IDs donnés
    @Override
    public List<Utilisateur> getUtilisateursByIds(List<Long> ids) {
        TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.id IN :ids", Utilisateur.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }
    
 // Méthode pour récupérer les utilisateurs à l'exception des IDs donnés
    @Override
    public List<Utilisateur> getUtilisateursExceptIds(List<Long> ids) {
    	 if (ids.isEmpty()) {
    	        // Si la liste des IDs est vide, cela signifie qu'aucun utilisateur ne doit être exclu
    	        // Dans ce cas, vous pouvez simplement récupérer tous les utilisateurs
    	        TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class);
    	        return query.getResultList();
    	    } else {
    	        // Si la liste des IDs n'est pas vide, excluez les utilisateurs correspondants à ces IDs
    	        TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.id NOT IN :ids", Utilisateur.class);
    	        query.setParameter("ids", ids);
    	        return query.getResultList();
    	    }
    }
    
    @Override
    public Utilisateur getUtilisateurById(Long id) {
    	Utilisateur utilisateur = em.find(Utilisateur.class, id);
    	return utilisateur;
    }
}