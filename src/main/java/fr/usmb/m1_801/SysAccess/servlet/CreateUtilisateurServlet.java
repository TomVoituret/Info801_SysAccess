package fr.usmb.m1_801.SysAccess.servlet;

import java.io.IOException;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import fr.usmb.m1_801.SysAccess.ejb.UtilisateurEJB;
import fr.usmb.m1_801.SysAccess.jpa.Utilisateur;

@WebServlet("/CreateUtilisateurServlet")
public class CreateUtilisateurServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Injection de la référence de l'EJB UtilisateurEJB
    @EJB
    private UtilisateurEJB utilisateurEJB;

    /**
     * Constructeur par défaut de la servlet.
     */
    public CreateUtilisateurServlet() {
        super();
    }

    /**
     * Méthode gérant les requêtes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    	long ticketId = Long.parseLong(request.getParameter("ticketId"));
    	
    	// Appel de la méthode createUtilisateur du UtilisateurEJB pour créer un nouveau utilisateur
        Utilisateur utilisateur = utilisateurEJB.addUtilisateur();
        
        
        // Configuration de l'attribut de requête pour transmettre le utilisateur à la vue
        request.setAttribute("utilisateur", utilisateur);

        // Transfert à la JSP d'affichage (showUtilisateur.jsp)
        request.getRequestDispatcher("/showUtilisateur.jsp").forward(request, response);
    }

    /**
     * Méthode gérant les requêtes HTTP POST (déleguée vers la méthode doGet).
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}