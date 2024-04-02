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
        user = "postgres",  // Mettez � jour avec le nom d'utilisateur correct
        password = "admin",  // Mettez � jour avec le mot de passe correct
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
     * M�thode pour ajouter un nouvel utilisateur � la base de donn�es.
     *
     * @param nom    Le nom de l'utilisateur.
     * @param prenom Le pr�nom de l'utilisateur.
     * @param type   Le type de l'utilisateur.
     * @return L'utilisateur ajout� avec son ID g�n�r�.
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
     * M�thode pour r�cup�rer tous les utilisateurs depuis la base de donn�es.
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
        // R�cup�rer l'utilisateur � supprimer
        Utilisateur utilisateurASupprimer = em.find(Utilisateur.class, id);
        
        // V�rifier si l'utilisateur existe
        if (utilisateurASupprimer != null) {
            // Si l'utilisateur existe, le supprimer
        	em.remove(utilisateurASupprimer);
            return utilisateurASupprimer;
        } else {
            // Si l'utilisateur n'existe pas, retourner null ou lancer une exception
            return null;
        }
    }
    
 // M�thode pour r�cup�rer les IDs des utilisateurs actuellement dans un b�timent sp�cifique
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

    // M�thode pour r�cup�rer les utilisateurs correspondant aux IDs donn�s
    @Override
    public List<Utilisateur> getUtilisateursByIds(List<Long> ids) {
        TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.id IN :ids", Utilisateur.class);
        query.setParameter("ids", ids);
        return query.getResultList();
    }
    
 // M�thode pour r�cup�rer les utilisateurs � l'exception des IDs donn�s
    @Override
    public List<Utilisateur> getUtilisateursExceptIds(List<Long> ids) {
    	 if (ids.isEmpty()) {
    	        // Si la liste des IDs est vide, cela signifie qu'aucun utilisateur ne doit �tre exclu
    	        // Dans ce cas, vous pouvez simplement r�cup�rer tous les utilisateurs
    	        TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class);
    	        return query.getResultList();
    	    } else {
    	        // Si la liste des IDs n'est pas vide, excluez les utilisateurs correspondants � ces IDs
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