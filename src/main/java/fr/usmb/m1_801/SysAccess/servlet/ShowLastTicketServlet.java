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



@WebServlet("/ShowLastTicketServlet")
public class ShowLastTicketServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	
	// injection de la reference de l'ejb
	@EJB
	private TicketEJB ejb;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowLastTicketServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// appel de l'ejb
		Ticket l = ejb.getLastTicket();		
		// ajout de la liste de mesures dans la requete
		request.setAttribute("ticket",l);
		// transfert a la JSP d'affichage
		request.getRequestDispatcher("/ShowLastTicket.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

