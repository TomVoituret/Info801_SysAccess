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
     * Constructeur par défaut de la servlet.
     */
    public JustificatifServlet() {
        super();
    }

    /**
     * Méthode gérant les requêtes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération de l'identifiant du ticket depuis les paramètres de la requête
        long ticketId = Long.parseLong(request.getParameter("ticketId"));

        // Récupération de la liste des paiements associés au ticket
        List<Payment> payments = paymentEJB.getPaymentsForTicket(ticketId);

        // Calcul du montant total des paiements pour ce ticket
        float finalAmount = 0;
        for (Payment payment : payments) {
            finalAmount += payment.getAmount();
        }

        // Configuration des attributs de requête pour afficher les détails du paiement dans la page JSP
        request.setAttribute("payment", payments.get(0)); // Utilisation du premier paiement pour les détails
        request.setAttribute("finalAmount", finalAmount);

        // Redirection vers la page d'affichage des détails de paiement (Payment.jsp)
        request.getRequestDispatcher("/Payment.jsp").forward(request, response);
    }

    /**
     * Méthode gérant les requêtes HTTP POST (déleguée vers la méthode doGet).
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}