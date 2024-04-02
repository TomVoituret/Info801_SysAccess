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

@WebServlet("/DeleteUtilisateurServlet")
public class DeleteUtilisateurServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Injection de la référence de l'EJB UtilisateurEJB
    @EJB
    private UtilisateurEJB utilisateurEJB;

    /**
     * Constructeur par défaut de la servlet.
     */
    public DeleteUtilisateurServlet() {
        super();
    }

    /**
     * Méthode gérant les requêtes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	Long id = Long.parseLong(request.getParameter("id"));

        
        Utilisateur utilisateurDelete = utilisateurEJB.deleteUtilisateur(id);


        request.getRequestDispatcher("/GetUserDeleteServlet").forward(request, response);
    }

    /**
     * Méthode gérant les requêtes HTTP POST.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doGet(request, response);
    }
}