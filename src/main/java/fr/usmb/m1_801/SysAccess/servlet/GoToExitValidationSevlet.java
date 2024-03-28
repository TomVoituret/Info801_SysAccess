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

@WebServlet("/GoToExitValidationSevlet")
public class GoToExitValidationSevlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur par d�faut de la servlet.
     */
    public GoToExitValidationSevlet() {
        super();
    }

    /**
     * M�thode g�rant les requ�tes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Configuration de l'attribut de requ�te pour indiquer que la requ�te provient de la page d'index
        request.setAttribute("fromIndex", true);

        // Redirection vers la page d'ExitValidation.jsp
        request.getRequestDispatcher("/ExitValidation.jsp").forward(request, response);
    }

    /**
     * M�thode g�rant les requ�tes HTTP POST (d�legu�e vers la m�thode doGet).
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}