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

import java.util.List;

import fr.usmb.m1_801.SysAccess.jpa.Ticket;
import fr.usmb.m1_801.SysAccess.jpa.Utilisateur;

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

	@Override
	public Utilisateur addUtilisateur() {
		// TODO Auto-generated method stub
		return null;
	}
}