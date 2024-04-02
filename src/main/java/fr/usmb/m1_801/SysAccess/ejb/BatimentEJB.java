package fr.usmb.m1_801.SysAccess.ejb;

import jakarta.annotation.Resource;
import jakarta.ejb.Local;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

import fr.usmb.m1_801.SysAccess.jpa.Batiment;


@Stateless
@LocalBean
@Local(BatimentRest.class)
public class BatimentEJB implements BatimentRest {

    @PersistenceContext(unitName = "SysAccessPU")
    private EntityManager em;

	@Override
	public Batiment addBatiment(String nomBat, Map<String, Boolean> autorisationsAcces) {
		Batiment batiment = new Batiment();
		batiment.setNom(nomBat);
		batiment.setAutorisationsAcces(autorisationsAcces);;
        
        em.persist(batiment);
        return batiment;
		
		
	}

	@Override
	public List<Batiment> getAllBatiments() {
		TypedQuery<Batiment> query = em.createQuery("SELECT b FROM Batiment b", Batiment.class);
        return query.getResultList();
	}

	@Override
	public Batiment addBatiment() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Batiment getBatimentById(Long id) {
	    return em.find(Batiment.class, id);
	}


    
}