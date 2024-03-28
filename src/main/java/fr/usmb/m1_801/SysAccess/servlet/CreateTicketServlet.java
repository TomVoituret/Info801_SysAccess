package fr.usmb.m1_801.SysAccess.servlet;

import java.io.IOException;
import java.util.Date;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import fr.usmb.m1_801.SysAccess.ejb.TicketEJB;
import fr.usmb.m1_801.SysAccess.jpa.Ticket;

@WebServlet("/CreateTicketServlet")
public class CreateTicketServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Injection de la référence de l'EJB TicketEJB
    @EJB
    private TicketEJB ticketEJB;

    /**
     * Constructeur par défaut de la servlet.
     */
    public CreateTicketServlet() {
        super();
    }

    /**
     * Méthode gérant les requêtes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Appel de la méthode createTicket du TicketEJB pour créer un nouveau ticket
        Ticket ticket = ticketEJB.createTicket();

        // Configuration de l'attribut de requête pour transmettre le ticket à la vue
        request.setAttribute("ticket", ticket);

        // Transfert à la JSP d'affichage (showTicket.jsp)
        request.getRequestDispatcher("/showTicket.jsp").forward(request, response);
    }

    /**
     * Méthode gérant les requêtes HTTP POST (déleguée vers la méthode doGet).
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}