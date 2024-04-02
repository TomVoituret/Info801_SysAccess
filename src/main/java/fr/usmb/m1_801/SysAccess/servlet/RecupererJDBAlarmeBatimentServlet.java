package fr.usmb.m1_801.SysAccess.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import fr.usmb.m1_801.SysAccess.ejb.BatimentEJB;
import fr.usmb.m1_801.SysAccess.ejb.JournalDeBordEJB;
import fr.usmb.m1_801.SysAccess.ejb.UtilisateurEJB;
import fr.usmb.m1_801.SysAccess.jpa.Batiment;
import fr.usmb.m1_801.SysAccess.jpa.JournalDeBord;
import fr.usmb.m1_801.SysAccess.jpa.Utilisateur;

@WebServlet("/RecupererJDBAlarmeBatimentServlet")
public class RecupererJDBAlarmeBatimentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Injection de la r�f�rence de l'EJB UtilisateurEJB
    @EJB
    private UtilisateurEJB utilisateurEJB;
    
    @EJB
    private BatimentEJB batimentEJB;
    
    @EJB
    private JournalDeBordEJB journalDeBordEJB;

    /**
     * Constructeur par d�faut de la servlet.
     */
    public RecupererJDBAlarmeBatimentServlet() {
        super();
    }

    /**
     * M�thode g�rant les requ�tes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	Long idBat = Long.parseLong(request.getParameter("batimentId"));
        
    	//Batiment batiment = batimentEJB.getBatimentById(idBat);
    	
    	//Utilisateur utilisateur = utilisateurEJB.getUtilisateurById(idUser);
    	
    	
    	List<JournalDeBord> listeJournaux = journalDeBordEJB.getJournalDeBordByBatimentAndNullSortie(idBat);
        
        // D�finir les utilisateurs comme attribut de requ�te pour la JSP
        request.setAttribute("listeJournaux", listeJournaux);

        // Transf�rer le contr�le � la JSP pour l'affichage des utilisateurs
        request.getRequestDispatcher("/InfosJourneauxDeBord.jsp").forward(request, response);
    }

    /**
     * M�thode g�rant les requ�tes HTTP POST.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doGet(request, response);
    }
}