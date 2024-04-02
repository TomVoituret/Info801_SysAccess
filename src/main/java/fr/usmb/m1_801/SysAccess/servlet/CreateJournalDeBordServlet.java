package fr.usmb.m1_801.SysAccess.servlet;


import java.io.IOException;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import fr.usmb.m1_801.SysAccess.ejb.JournalDeBordEJB;
import fr.usmb.m1_801.SysAccess.ejb.UtilisateurEJB;
import fr.usmb.m1_801.SysAccess.jpa.JournalDeBord;
import fr.usmb.m1_801.SysAccess.jpa.Utilisateur;

@WebServlet("/CreateJournalDeBordServlet")
public class CreateJournalDeBordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Injection de la référence de l'EJB UtilisateurEJB
    @EJB
    private UtilisateurEJB utilisateurEJB;
    
    @EJB
    private JournalDeBordEJB journalDeBordEJB;

    /**
     * Constructeur par défaut de la servlet.
     */
    public CreateJournalDeBordServlet() {
        super();
    }

    /**
     * Méthode gérant les requêtes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	Long idUser = Long.parseLong(request.getParameter("currentUserId"));
    	Long idBat = Long.parseLong(request.getParameter("currentBatId"));
    	
        
        journalDeBordEJB.addJournalDeBord(idUser, idBat);
        
        request.setAttribute("acces", true);
        request.setAttribute("dejaCliquer", true);

        request.getRequestDispatcher("/GetInfosForTestServlet").forward(request, response);
    }

    /**
     * Méthode gérant les requêtes HTTP POST.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doGet(request, response);
    }
}