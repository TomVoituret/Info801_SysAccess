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
	 * Méthode pour ajouter un nouvel utilisateur à la base de données.
	 *
	 * @param nom    Le nom de l'utilisateur.
	 * @param prenom Le prénom de l'utilisateur.
	 * @param type   Le type de l'utilisateur.
	 * @return L'utilisateur ajouté avec son ID généré.
	 */
	Batiment addBatiment();

	/**
	 * Méthode pour récupérer tous les utilisateurs depuis la base de données.
	 *
	 * @return La liste de tous les utilisateurs.
	 */
	List<Batiment> getAllBatiments();

	Batiment addBatiment(String nomBat, Map<String, Boolean> autorisationsAcces);

	Batiment getBatimentById(Long id);

}