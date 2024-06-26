package fr.usmb.m1_801.SysAccess.ejb;

import java.util.List;

import fr.usmb.m1_801.SysAccess.jpa.Utilisateur;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("utilisateur")
public interface UtilisateurRest {

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
	Utilisateur addUtilisateur(String nom, String prenom, String type);

	/**
	 * M�thode pour r�cup�rer tous les utilisateurs depuis la base de donn�es.
	 *
	 * @return La liste de tous les utilisateurs.
	 */
	List<Utilisateur> getAllUtilisateurs();

	Utilisateur deleteUtilisateur(Long id);

	List<Long> getUtilisateurIdsInBat(Long idBat);

	List<Utilisateur> getUtilisateursByIds(List<Long> ids);

	List<Utilisateur> getUtilisateursExceptIds(List<Long> ids);

	List<Long> getUtilisateurIdsInAllBat();

	Utilisateur getUtilisateurById(Long id);


}