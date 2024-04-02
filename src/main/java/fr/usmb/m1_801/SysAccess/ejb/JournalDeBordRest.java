package fr.usmb.m1_801.SysAccess.ejb;
import java.util.List;

import fr.usmb.m1_801.SysAccess.jpa.JournalDeBord;
import fr.usmb.m1_801.SysAccess.jpa.Utilisateur;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("journalDeBord")
public interface JournalDeBordRest {
	
	@Path("add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	JournalDeBord addJournalDeBord(Long idUser, Long idBat);
	
	JournalDeBord UpdateTimeJournalFromUserAndBatId(Long idUser, Long idBat);

	List<JournalDeBord> getAllJournalDeBord();

	List<JournalDeBord> getJournalDeBordByBatimentAndNullSortie(Long idBat);

}