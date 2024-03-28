package fr.usmb.m1_801.SysAccess.servlet;

import java.io.IOException;
import java.util.Date;

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

@WebServlet("/ExitValidationServlet")
public class ExitValidationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Injection de la référence de l'EJB PaymentEJB
    @EJB
    private PaymentEJB paymentEJB;

    // Injection de la référence de l'EJB TicketEJB
    @EJB
    private TicketEJB ticketEJB;

    /**
     * Constructeur par défaut de la servlet.
     */
    public ExitValidationServlet() {
        super();
    }

    /**
     * Méthode gérant les requêtes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération de l'ID du ticket depuis les paramètres de la requête
        long ticketId = Long.parseLong(request.getParameter("ticketId"));

        // Recherche du ticket correspondant à l'ID
        Ticket t = ticketEJB.findTicket(ticketId);

        // Vérification si le ticket existe et n'a pas encore été validé pour la sortie
        if (t != null && t.getExitDate() == null) {
            // Récupération du dernier paiement associé au ticket
            Payment p = paymentEJB.getLastPaymentFromTicket(ticketId);

            if (p != null) {
                Date datePaiement = p.getPaymentDate();
                Date now = new Date();

                // Calcul de la différence en millisecondes entre les deux dates
                long millisecondsDifference = now.getTime() - datePaiement.getTime();

                // Conversion de la différence en minutes
                long minutesDifference = millisecondsDifference / (60 * 1000);

                // Vérification si la différence est supérieure à 2 minutes
                if (minutesDifference > 2) {
                    // Configuration des attributs de requête pour indiquer le statut de sortie et de paiement
                    request.setAttribute("sortie", false);
                    request.setAttribute("payer", true);
                    request.getRequestDispatcher("/ExitValidation.jsp").forward(request, response);
                } else {
                    // Mise à jour de la date de sortie du ticket
                    t.setExitDate(now);
                    ticketEJB.updateTicket(t);

                    // Configuration de l'attribut de requête pour indiquer le statut de sortie
                    request.setAttribute("sortie", true);
                    request.getRequestDispatcher("/ExitValidation.jsp").forward(request, response);
                }
            }

            // Configuration des attributs de requête pour indiquer le statut de sortie et de paiement
            request.setAttribute("sortie", false);
            request.setAttribute("payer", false);
            request.getRequestDispatcher("/ExitValidation.jsp").forward(request, response);
        }

        // Redirection vers la page d'index en cas de numéro de ticket incorrect
        request.setAttribute("badNum", true);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    /**
     * Méthode gérant les requêtes HTTP POST (déleguée vers la méthode doGet).
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}