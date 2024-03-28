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

    // Injection de la r�f�rence de l'EJB UtilisateurEJB
    @EJB
    private UtilisateurEJB utilisateurEJB;

    /**
     * Constructeur par d�faut de la servlet.
     */
    public CreateUtilisateurServlet() {
        super();
    }

    /**
     * M�thode g�rant les requ�tes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    	long ticketId = Long.parseLong(request.getParameter("ticketId"));
    	
    	// Appel de la m�thode createUtilisateur du UtilisateurEJB pour cr�er un nouveau utilisateur
        Utilisateur utilisateur = utilisateurEJB.addUtilisateur();
        
        
        // Configuration de l'attribut de requ�te pour transmettre le utilisateur � la vue
        request.setAttribute("utilisateur", utilisateur);

        // Transfert � la JSP d'affichage (showUtilisateur.jsp)
        request.getRequestDispatcher("/showUtilisateur.jsp").forward(request, response);
    }

    /**
     * M�thode g�rant les requ�tes HTTP POST (d�legu�e vers la m�thode doGet).
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}