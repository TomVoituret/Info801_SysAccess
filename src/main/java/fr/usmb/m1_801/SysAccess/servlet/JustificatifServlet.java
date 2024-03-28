package fr.usmb.m1_801.SysAccess.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import fr.usmb.m1_801.SysAccess.ejb.PaymentEJB;
import fr.usmb.m1_801.SysAccess.ejb.TicketEJB;
import fr.usmb.m1_801.SysAccess.jpa.Payment;
import fr.usmb.m1_801.SysAccess.jpa.Ticket;

@WebServlet("/JustificatifServlet")
public class JustificatifServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private PaymentEJB paymentEJB;

    @EJB
    private TicketEJB ticketEJB;

    /**
     * Constructeur par d�faut de la servlet.
     */
    public JustificatifServlet() {
        super();
    }

    /**
     * M�thode g�rant les requ�tes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // R�cup�ration de l'identifiant du ticket depuis les param�tres de la requ�te
        long ticketId = Long.parseLong(request.getParameter("ticketId"));

        // R�cup�ration de la liste des paiements associ�s au ticket
        List<Payment> payments = paymentEJB.getPaymentsForTicket(ticketId);

        // Calcul du montant total des paiements pour ce ticket
        float finalAmount = 0;
        for (Payment payment : payments) {
            finalAmount += payment.getAmount();
        }

        // Configuration des attributs de requ�te pour afficher les d�tails du paiement dans la page JSP
        request.setAttribute("payment", payments.get(0)); // Utilisation du premier paiement pour les d�tails
        request.setAttribute("finalAmount", finalAmount);

        // Redirection vers la page d'affichage des d�tails de paiement (Payment.jsp)
        request.getRequestDispatcher("/Payment.jsp").forward(request, response);
    }

    /**
     * M�thode g�rant les requ�tes HTTP POST (d�legu�e vers la m�thode doGet).
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}