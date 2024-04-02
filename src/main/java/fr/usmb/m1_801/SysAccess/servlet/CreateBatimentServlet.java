package fr.usmb.m1_801.SysAccess.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import fr.usmb.m1_801.SysAccess.ejb.BatimentEJB;
import fr.usmb.m1_801.SysAccess.jpa.Batiment;

@WebServlet("/CreateBatimentServlet")
public class CreateBatimentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Injection de la référence de l'EJB UtilisateurEJB
    @EJB
    private BatimentEJB batimentEJB;

    /**
     * Constructeur par défaut de la servlet.
     */
    public CreateBatimentServlet() {
        super();
    }

    /**
     * Méthode gérant les requêtes HTTP GET.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	String nomBat = request.getParameter("nomBat");
    	
        Map<String, Boolean> autorisationsAcces = new HashMap<>();

        autorisationsAcces.put("Enseignant", Boolean.parseBoolean(request.getParameter("typeEnseignant")));
        autorisationsAcces.put("PersonnelAdministratif", Boolean.parseBoolean(request.getParameter("typePersonnelAdministratif")));
        autorisationsAcces.put("EtudiantInfo", Boolean.parseBoolean(request.getParameter("typeEtudiantInfo")));
        autorisationsAcces.put("EtudiantStaps", Boolean.parseBoolean(request.getParameter("typeEtudiantStaps")));
        
        Batiment batimentAjoute = batimentEJB.addBatiment(nomBat, autorisationsAcces);

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    /**
     * Méthode gérant les requêtes HTTP POST.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doGet(request, response);
    }
}