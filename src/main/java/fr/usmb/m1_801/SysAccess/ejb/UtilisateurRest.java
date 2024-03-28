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
    Utilisateur addUtilisateur();

}