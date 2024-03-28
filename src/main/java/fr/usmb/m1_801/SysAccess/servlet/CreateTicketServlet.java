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

    // Injection de la r�f�rence de l'EJB TicketEJB
    @EJB
    private TicketEJB ticketEJB;

    /**
     * Constructeur par d�faut de la servlet.
     */
    public CreateTicketServlet() {
        super();
    }

    /**
     * M�thode g�rant les requ�tes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Appel de la m�thode createTicket du TicketEJB pour cr�er un nouveau ticket
        Ticket ticket = ticketEJB.createTicket();

        // Configuration de l'attribut de requ�te pour transmettre le ticket � la vue
        request.setAttribute("ticket", ticket);

        // Transfert � la JSP d'affichage (showTicket.jsp)
        request.getRequestDispatcher("/showTicket.jsp").forward(request, response);
    }

    /**
     * M�thode g�rant les requ�tes HTTP POST (d�legu�e vers la m�thode doGet).
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}