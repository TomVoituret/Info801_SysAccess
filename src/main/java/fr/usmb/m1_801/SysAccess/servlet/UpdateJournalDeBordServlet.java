package fr.usmb.m1_801.SysAccess.servlet;

import java.io.IOException;
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

@WebServlet("/UpdateJournalDeBordServlet")
public class UpdateJournalDeBordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Injection de la référence de l'EJB UtilisateurEJB
    @EJB
    private UtilisateurEJB utilisateurEJB;
    
    @EJB
    private BatimentEJB batimentEJB;
    
    @EJB
    private JournalDeBordEJB journalDeBordEJB;

    /**
     * Constructeur par défaut de la servlet.
     */
    public UpdateJournalDeBordServlet() {
        super();
    }

    /**
     * Méthode gérant les requêtes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	Long idUser = Long.parseLong(request.getParameter("utilisateurId"));
    	Long idBat = Long.parseLong(request.getParameter("batimentId"));
        
    	//Batiment batiment = batimentEJB.getBatimentById(idBat);
    	
    	//Utilisateur utilisateur = utilisateurEJB.getUtilisateurById(idUser);
    	
    	journalDeBordEJB.UpdateTimeJournalFromUserAndBatId(idUser, idBat);
    	
        request.setAttribute("utilisateurId", idUser);
        
        request.getRequestDispatcher("/GetInfoForSortieServlet?id=" + idBat).forward(request, response);
    }

    /**
     * Méthode gérant les requêtes HTTP POST.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doGet(request, response);
    }
}