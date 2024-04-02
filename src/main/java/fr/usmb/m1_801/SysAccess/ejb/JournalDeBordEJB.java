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
import java.util.Date;
import java.util.List;

import fr.usmb.m1_801.SysAccess.jpa.Batiment;
import fr.usmb.m1_801.SysAccess.jpa.JournalDeBord;
import fr.usmb.m1_801.SysAccess.jpa.Utilisateur;


@WebService
@Stateless
@LocalBean
@Local
public class JournalDeBordEJB implements JournalDeBordRest {

    @PersistenceContext(unitName = "SysAccessPU")
    private EntityManager em;

    @Override
    public JournalDeBord addJournalDeBord(Long idUser, Long idBat) {
        // Récupérer l'utilisateur et le bâtiment à partir de leurs identifiants
        Utilisateur utilisateur = em.find(Utilisateur.class, idUser);
        Batiment batiment = em.find(Batiment.class, idBat);
        
        // Créer une nouvelle instance de JournalDeBord
        JournalDeBord journalDeBord = new JournalDeBord();
        journalDeBord.setHeureEntree(new Date()); // Définir l'heure d'entrée actuelle
        journalDeBord.setUtilisateur(utilisateur);
        journalDeBord.setBatiment(batiment);
        
        // Enregistrer le journal de bord dans la base de données
        em.persist(journalDeBord);
        
        return journalDeBord;
    }

    @Override
    public JournalDeBord UpdateTimeJournalFromUserAndBatId(Long idUser, Long idBat) {
        // Récupérer le JournalDeBord correspondant à l'utilisateur, au bâtiment et où l'heure de sortie est null
        Query query = em.createQuery("SELECT j FROM JournalDeBord j WHERE j.utilisateur.id = :userId AND j.batiment.id = :batId AND j.heureSortie IS NULL", JournalDeBord.class);
        query.setParameter("userId", idUser);
        query.setParameter("batId", idBat);
        List<JournalDeBord> results = query.getResultList();
        
        // Vérifier s'il existe un journal de bord correspondant
        if (!results.isEmpty()) {
            // Mettre à jour l'heure de sortie et enregistrer
            JournalDeBord journalDeBord = results.get(0); // Supposons qu'il n'y a qu'un seul journal de bord correspondant
            journalDeBord.setHeureSortie(new Date()); // Mettre l'heure actuelle comme heure de sortie
            em.persist(journalDeBord);
            return journalDeBord;
        } else {
            // Si aucun journal de bord correspondant n'est trouvé
            return null;
        }
    }
    
    
    @Override
    public List<JournalDeBord> getAllJournalDeBord() {
    	TypedQuery<JournalDeBord> query = em.createQuery("SELECT j FROM JournalDeBord j ORDER BY j.batiment.id, j.id", JournalDeBord.class);
    	return query.getResultList();
    }
    
    @Override
    public List<JournalDeBord> getJournalDeBordByBatimentAndNullSortie(Long idBat) {
        TypedQuery<JournalDeBord> query = em.createQuery("SELECT j FROM JournalDeBord j WHERE j.batiment.id = :batId AND j.heureSortie IS NULL", JournalDeBord.class);
        query.setParameter("batId", idBat);
        return query.getResultList();
    }


}