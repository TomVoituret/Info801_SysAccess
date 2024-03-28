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

	// Injection de la référence de l'EJB TicketEJB
	@EJB
	private TicketEJB ejb;

	// Injection de la référence de l'EJB PaymentEJB
	@EJB
	private PaymentEJB Pejb;

	/**
	 * Constructeur par défaut de la servlet.
	 */
	public CalculeMontantServlet() {
		super();
	}

	/**
	 * Méthode gérant les requêtes HTTP GET.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récupération de l'ID du ticket depuis les paramètres de la requête
		long ticketId = Long.parseLong(request.getParameter("ticketId"));

		// Appel de la méthode findTicket du TicketEJB pour récupérer le ticket correspondant
		Ticket l = ejb.findTicket(ticketId);

		// Test si le ticket exist ou qu'il n'a pas déjà été utilisé (date de sortie existante)
		if (l != null && l.getExitDate() == null) {
			// Récupération du dernier paiement associé au ticket
			Payment p = Pejb.getLastPaymentFromTicket(ticketId);
			Date entryDate;

			// Si un paiement existe, utilisez sa date comme date d'entrée, sinon utilisez la date d'entrée du ticket
			if (p != null) {
				entryDate = p.getPaymentDate();
			} else {
				entryDate = l.getEntryDate();
			}

			// Obtention de la date et de l'heure actuelles
			Date now = new Date();

			// Création des objets Calendar pour les deux dates
			Calendar entryCalendar = Calendar.getInstance();
			entryCalendar.setTime(entryDate);

			Calendar nowCalendar = Calendar.getInstance();
			nowCalendar.setTime(now);

			// Obtention de la différence en millisecondes entre les deux dates
			long millisecondsDifference = nowCalendar.getTimeInMillis() - entryCalendar.getTimeInMillis();

			// Conversion de la différence en minutes
			long minutesDifference = millisecondsDifference / (60 * 1000);

			// Calcul du montant en fonction du temps passé
			float Mont = (float) (minutesDifference * 0.20);

			// Configuration des attributs de requête pour transmettre des informations à la vue
			request.setAttribute("montant", Mont);
			request.setAttribute("ticketId", ticketId);

			// Transfert à la JSP d'affichage
			request.getRequestDispatcher("/Payment.jsp").forward(request, response);
		} else {
			// Si le ticket est introuvable ou s'il a déjà été payé, configurez un attribut de requête pour indiquer une erreur
			request.setAttribute("badNum", true);
			// Redirection vers la page index.jsp
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

	/**
	 * Méthode gérant les requêtes HTTP POST (déleguée vers la méthode doGet).
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
