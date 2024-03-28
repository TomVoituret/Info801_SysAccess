package fr.usmb.m1_801.SysAccess.servlet;

import java.io.IOException;
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

@WebServlet("/ShowAllPaymentsServlet")
public class ShowAllPaymentsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // injection de la reference de l'ejb
    @EJB
    private PaymentEJB paymentEJB;

    public ShowAllPaymentsServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // appel de l'ejb pour récupérer la liste de tous les tickets
        List<Payment> paymentList = paymentEJB.getAllPayments();

        // ajout de la liste de tickets dans la requete
        request.setAttribute("payments", paymentList);

        // transfert à la JSP d'affichage
        request.getRequestDispatcher("/showPayment.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}