package fr.usmb.m1_801.SysAccess.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;
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



@WebServlet("/CalculeMontantServlet")
public class CalculeMontantServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Injection de la r�f�rence de l'EJB TicketEJB
	@EJB
	private TicketEJB ejb;

	// Injection de la r�f�rence de l'EJB PaymentEJB
	@EJB
	private PaymentEJB Pejb;

	/**
	 * Constructeur par d�faut de la servlet.
	 */
	public CalculeMontantServlet() {
		super();
	}

	/**
	 * M�thode g�rant les requ�tes HTTP GET.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// R�cup�ration de l'ID du ticket depuis les param�tres de la requ�te
		long ticketId = Long.parseLong(request.getParameter("ticketId"));

		// Appel de la m�thode findTicket du TicketEJB pour r�cup�rer le ticket correspondant
		Ticket l = ejb.findTicket(ticketId);

		// Test si le ticket exist ou qu'il n'a pas d�j� �t� utilis� (date de sortie existante)
		if (l != null && l.getExitDate() == null) {
			// R�cup�ration du dernier paiement associ� au ticket
			Payment p = Pejb.getLastPaymentFromTicket(ticketId);
			Date entryDate;

			// Si un paiement existe, utilisez sa date comme date d'entr�e, sinon utilisez la date d'entr�e du ticket
			if (p != null) {
				entryDate = p.getPaymentDate();
			} else {
				entryDate = l.getEntryDate();
			}

			// Obtention de la date et de l'heure actuelles
			Date now = new Date();

			// Cr�ation des objets Calendar pour les deux dates
			Calendar entryCalendar = Calendar.getInstance();
			entryCalendar.setTime(entryDate);

			Calendar nowCalendar = Calendar.getInstance();
			nowCalendar.setTime(now);

			// Obtention de la diff�rence en millisecondes entre les deux dates
			long millisecondsDifference = nowCalendar.getTimeInMillis() - entryCalendar.getTimeInMillis();

			// Conversion de la diff�rence en minutes
			long minutesDifference = millisecondsDifference / (60 * 1000);

			// Calcul du montant en fonction du temps pass�
			float Mont = (float) (minutesDifference * 0.20);

			// Configuration des attributs de requ�te pour transmettre des informations � la vue
			request.setAttribute("montant", Mont);
			request.setAttribute("ticketId", ticketId);

			// Transfert � la JSP d'affichage
			request.getRequestDispatcher("/Payment.jsp").forward(request, response);
		} else {
			// Si le ticket est introuvable ou s'il a d�j� �t� pay�, configurez un attribut de requ�te pour indiquer une erreur
			request.setAttribute("badNum", true);
			// Redirection vers la page index.jsp
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

	/**
	 * M�thode g�rant les requ�tes HTTP POST (d�legu�e vers la m�thode doGet).
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
