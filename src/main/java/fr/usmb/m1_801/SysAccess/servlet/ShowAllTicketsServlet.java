package fr.usmb.m1_801.SysAccess.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import fr.usmb.m1_801.SysAccess.ejb.TicketEJB;
import fr.usmb.m1_801.SysAccess.jpa.Ticket;

@WebServlet("/ShowAllTicketsServlet")
public class ShowAllTicketsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // injection de la reference de l'ejb
    @EJB
    private TicketEJB ticketEJB;

    public ShowAllTicketsServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // appel de l'ejb pour r�cup�rer la liste de tous les tickets
        List<Ticket> ticketList = ticketEJB.getAllTickets();

        // ajout de la liste de tickets dans la requete
        request.setAttribute("tickets", ticketList);

        // transfert � la JSP d'affichage
        request.getRequestDispatcher("/showTicket.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}