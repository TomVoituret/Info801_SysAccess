package fr.usmb.m1_801.SysAccess.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.usmb.m1_801.SysAccess.ejb.*;
import fr.usmb.m1_801.SysAccess.jpa.*;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/GetInfosForTestServlet")
public class GetInfosForTestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Injection de la r�f�rence de l'EJB UtilisateurEJB
    @EJB
    private UtilisateurEJB utilisateurEJB;
    @EJB
    private BatimentEJB batimentEJB;

    /**
     * Constructeur par d�faut de la servlet.
     */
    public GetInfosForTestServlet() {
        super();
    }

    /**
     * M�thode g�rant les requ�tes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	
        // R�cup�rer tous les utilisateurs depuis l'EJB
        List<Utilisateur> utilisateurs = utilisateurEJB.getUtilisateursExceptIds(utilisateurEJB.getUtilisateurIdsInAllBat() );
        
        // D�finir les utilisateurs comme attribut de requ�te pour la JSP
        request.setAttribute("utilisateurs", utilisateurs);
        
        
        List<Batiment> batiments = batimentEJB.getAllBatiments();
        
        request.setAttribute("batiments", batiments);
        
        Object acces = request.getAttribute("acces");
        if (acces != null) {
            // Si acces n'est pas null, vous pouvez le transmettre � votre page JSP
            request.setAttribute("acces", acces);
        } else {
            // Si acces est null, vous pouvez d�finir une valeur par d�faut ou ne rien faire
            request.setAttribute("acces", false); // Par exemple, d�finissez une valeur par d�faut
        }
        
        Object dejaCliquer = request.getAttribute("dejaCliquer");
        if (dejaCliquer != null) {
            // Si acces n'est pas null, vous pouvez le transmettre � votre page JSP
            request.setAttribute("dejaCliquer", acces);
        } else {
            request.setAttribute("dejaCliquer", false); // Par exemple, d�finissez une valeur par d�faut
        }
        
        Object idUser = request.getAttribute("currentUserId");
        if (idUser != null) {
        	request.setAttribute("currentUserId", idUser);
        } 
        
        Object idBat = request.getAttribute("currentBatId");
        if (idUser != null) {
        	request.setAttribute("currentBatId", idBat);
        } else {
            // Si acces est null, vous pouvez d�finir une valeur par d�faut ou ne rien faire
            request.setAttribute("currentBatId", "test2"); // Par exemple, d�finissez une valeur par d�faut
        }
        
        

        // Transf�rer le contr�le � la JSP pour l'affichage des utilisateurs
        request.getRequestDispatcher("/Fonctionnement.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}