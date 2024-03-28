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

    // Injection des EJB (Enterprise JavaBeans) n�cessaires
    @EJB
    private PaymentEJB paymentEJB;

    @EJB
    private TicketEJB ticketEJB;

    // Constructeur par d�faut de la servlet
    public PaymentServlet() {
        super();
    }

    // M�thode g�rant les requ�tes HTTP GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // R�cup�ration des param�tres de la requ�te
        long ticketId = Long.parseLong(request.getParameter("ticketId"));
        double amount = Double.parseDouble(request.getParameter("montant"));
        String paymentType = request.getParameter("paymentType");
        
        // Appel de la m�thode createPayment du PaymentEJB pour cr�er un nouveau paiement
        paymentEJB.createPayment(ticketId, amount, paymentType);
        
        // Mise en place des attributs de requ�te pour la transmission d'informations � la vue
        request.setAttribute("ticketId", ticketId);
        request.setAttribute("recap", true);
        
        // Redirection vers la page Payment.jsp
        request.getRequestDispatcher("/Payment.jsp").forward(request, response);
    }

    // M�thode g�rant les requ�tes HTTP POST (d�legu�e vers la m�thode doGet)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}