package fr.usmb.m1_801.SysAccess.servlet;

import java.io.IOException;
import java.util.List;

import fr.usmb.m1_801.SysAccess.ejb.*;
import fr.usmb.m1_801.SysAccess.jpa.*;
import jakarta.ejb.EJB;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/GetInfoForSortieServlet")
public class GetInfoForSortieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Injection de la référence de l'EJB UtilisateurEJB
    @EJB
    private UtilisateurEJB utilisateurEJB;
    @EJB
    private BatimentEJB batimentEJB;

    /**
     * Constructeur par défaut de la servlet.
     */
    public GetInfoForSortieServlet() {
        super();
    }

    /**
     * Méthode gérant les requêtes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	Long idBat = Long.parseLong(request.getParameter("id"));
    	
        // Récupérer tous les utilisateurs depuis l'EJB
        List<Utilisateur> utilisateurs = utilisateurEJB.getUtilisateursByIds(utilisateurEJB.getUtilisateurIdsInBat(idBat));
        
        //System.out.println(utilisateurs.toString());
        // Définir les utilisateurs comme attribut de requête pour la JSP
        request.setAttribute("utilisateurs", utilisateurs);
        
        
        Batiment batiment = batimentEJB.getBatimentById(idBat);
        
        request.setAttribute("batiment", batiment);
        //request.setAttribute("acces", null);
        
        // Transférer le contrôle à la JSP pour l'affichage des utilisateurs
        request.getRequestDispatcher("/SortieDepuisBatiment.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    

}