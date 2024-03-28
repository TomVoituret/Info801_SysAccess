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

    // Injection de la r�f�rence de l'EJB PaymentEJB
    @EJB
    private PaymentEJB paymentEJB;

    // Injection de la r�f�rence de l'EJB TicketEJB
    @EJB
    private TicketEJB ticketEJB;

    /**
     * Constructeur par d�faut de la servlet.
     */
    public ExitValidationServlet() {
        super();
    }

    /**
     * M�thode g�rant les requ�tes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // R�cup�ration de l'ID du ticket depuis les param�tres de la requ�te
        long ticketId = Long.parseLong(request.getParameter("ticketId"));

        // Recherche du ticket correspondant � l'ID
        Ticket t = ticketEJB.findTicket(ticketId);

        // V�rification si le ticket existe et n'a pas encore �t� valid� pour la sortie
        if (t != null && t.getExitDate() == null) {
            // R�cup�ration du dernier paiement associ� au ticket
            Payment p = paymentEJB.getLastPaymentFromTicket(ticketId);

            if (p != null) {
                Date datePaiement = p.getPaymentDate();
                Date now = new Date();

                // Calcul de la diff�rence en millisecondes entre les deux dates
                long millisecondsDifference = now.getTime() - datePaiement.getTime();

                // Conversion de la diff�rence en minutes
                long minutesDifference = millisecondsDifference / (60 * 1000);

                // V�rification si la diff�rence est sup�rieure � 2 minutes
                if (minutesDifference > 2) {
                    // Configuration des attributs de requ�te pour indiquer le statut de sortie et de paiement
                    request.setAttribute("sortie", false);
                    request.setAttribute("payer", true);
                    request.getRequestDispatcher("/ExitValidation.jsp").forward(request, response);
                } else {
                    // Mise � jour de la date de sortie du ticket
                    t.setExitDate(now);
                    ticketEJB.updateTicket(t);

                    // Configuration de l'attribut de requ�te pour indiquer le statut de sortie
                    request.setAttribute("sortie", true);
                    request.getRequestDispatcher("/ExitValidation.jsp").forward(request, response);
                }
            }

            // Configuration des attributs de requ�te pour indiquer le statut de sortie et de paiement
            request.setAttribute("sortie", false);
            request.setAttribute("payer", false);
            request.getRequestDispatcher("/ExitValidation.jsp").forward(request, response);
        }

        // Redirection vers la page d'index en cas de num�ro de ticket incorrect
        request.setAttribute("badNum", true);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    /**
     * M�thode g�rant les requ�tes HTTP POST (d�legu�e vers la m�thode doGet).
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}