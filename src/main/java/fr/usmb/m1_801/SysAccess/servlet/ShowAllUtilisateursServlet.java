package fr.usmb.m1_801.SysAccess.servlet;

import java.io.IOException;
import java.util.List;

import fr.usmb.m1_801.SysAccess.ejb.UtilisateurEJB;
import fr.usmb.m1_801.SysAccess.jpa.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ShowAllUtilisateursServlet")
public class ShowAllUtilisateursServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Injection de la référence de l'EJB UtilisateurEJB
    @EJB
    private UtilisateurEJB utilisateurEJB;

    /**
     * Constructeur par défaut de la servlet.
     */
    public ShowAllUtilisateursServlet() {
        super();
    }

    /**
     * Méthode gérant les requêtes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupérer tous les utilisateurs depuis l'EJB
        List<Utilisateur> utilisateurs = utilisateurEJB.getAllUtilisateurs();

        
        
        // Définir les utilisateurs comme attribut de requête pour la JSP
        request.setAttribute("utilisateurs", utilisateurs);

        // Transférer le contrôle à la JSP pour l'affichage des utilisateurs
        request.getRequestDispatcher("/DeleteUtilisateur.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}