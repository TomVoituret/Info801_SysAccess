package fr.usmb.m1_801.SysAccess.ejb;

import java.util.List;
import java.util.Map;

import fr.usmb.m1_801.SysAccess.jpa.Batiment;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("batiment")
public interface BatimentRest {

    @Path("add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	/**
	 * M�thode pour ajouter un nouvel utilisateur � la base de donn�es.
	 *
	 * @param nom    Le nom de l'utilisateur.
	 * @param prenom Le pr�nom de l'utilisateur.
	 * @param type   Le type de l'utilisateur.
	 * @return L'utilisateur ajout� avec son ID g�n�r�.
	 */
	Batiment addBatiment();

	/**
	 * M�thode pour r�cup�rer tous les utilisateurs depuis la base de donn�es.
	 *
	 * @return La liste de tous les utilisateurs.
	 */
	List<Batiment> getAllBatiments();

	Batiment addBatiment(String nomBat, Map<String, Boolean> autorisationsAcces);

	Batiment getBatimentById(Long id);

}