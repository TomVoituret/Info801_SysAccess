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

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Injection des EJB (Enterprise JavaBeans) nécessaires
    @EJB
    private PaymentEJB paymentEJB;

    @EJB
    private TicketEJB ticketEJB;

    // Constructeur par défaut de la servlet
    public PaymentServlet() {
        super();
    }

    // Méthode gérant les requêtes HTTP GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération des paramètres de la requête
        long ticketId = Long.parseLong(request.getParameter("ticketId"));
        double amount = Double.parseDouble(request.getParameter("montant"));
        String paymentType = request.getParameter("paymentType");
        
        // Appel de la méthode createPayment du PaymentEJB pour créer un nouveau paiement
        paymentEJB.createPayment(ticketId, amount, paymentType);
        
        // Mise en place des attributs de requête pour la transmission d'informations à la vue
        request.setAttribute("ticketId", ticketId);
        request.setAttribute("recap", true);
        
        // Redirection vers la page Payment.jsp
        request.getRequestDispatcher("/Payment.jsp").forward(request, response);
    }

    // Méthode gérant les requêtes HTTP POST (déleguée vers la méthode doGet)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}