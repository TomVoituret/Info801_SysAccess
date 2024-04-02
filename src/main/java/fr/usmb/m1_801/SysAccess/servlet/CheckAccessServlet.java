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
import fr.usmb.m1_801.SysAccess.ejb.UtilisateurEJB;
import fr.usmb.m1_801.SysAccess.jpa.Batiment;
import fr.usmb.m1_801.SysAccess.jpa.Utilisateur;

@WebServlet("/CheckAccessServlet")
public class CheckAccessServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Injection de la r�f�rence de l'EJB UtilisateurEJB
    @EJB
    private UtilisateurEJB utilisateurEJB;
    
    @EJB
    private BatimentEJB batimentEJB;

    /**
     * Constructeur par d�faut de la servlet.
     */
    public CheckAccessServlet() {
        super();
    }

    /**
     * M�thode g�rant les requ�tes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	Long idUser = Long.parseLong(request.getParameter("utilisateurId"));
    	Long idBat = Long.parseLong(request.getParameter("batimentId"));
        
    	Batiment batiment = batimentEJB.getBatimentById(idBat);
    	
    	Utilisateur utilisateur = utilisateurEJB.getUtilisateurById(idUser);

    	// R�cup�rer les autorisations d'acc�s du b�timent
        Map<String, Boolean> autorisationsAcces = batiment.getAutorisationsAcces();
        
        boolean isTypeAllowed = false;
        if (autorisationsAcces.containsKey(utilisateur.getType())) {
        	
            isTypeAllowed = autorisationsAcces.get(utilisateur.getType());
        }
        request.setAttribute("acces", isTypeAllowed);
        request.setAttribute("currentUserId", idUser);
        request.setAttribute("currentBatId", idBat);

        request.getRequestDispatcher("/GetInfosForTestServlet").forward(request, response);
    }

    /**
     * M�thode g�rant les requ�tes HTTP POST.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doGet(request, response);
    }
}