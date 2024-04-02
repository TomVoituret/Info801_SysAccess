<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>V�rification de sortie du b�timent</title>
</head>
<body>
    <h2>Informations sur le b�timent</h2>
    
    <p>Nom du b�timent : ${batiment.nom}</p>
    <!-- Ajoutez ici d'autres informations sur le b�timent si n�cessaire -->
    
    <h2>Choix de l'utilisateur pour la v�rification de sortie</h2>
    
    <form id="checkSortieForm">
        <input type="hidden" id="batimentId" name="batimentId" value="${batiment.id}">
        <label for="utilisateur">S�lectionnez un utilisateur :</label>
        <select id="utilisateur" name="utilisateur">
            <c:forEach items="${utilisateurs}" var="utilisateur">
                <option value="${utilisateur.id}">${utilisateur.nom} ${utilisateur.prenom}</option>
            </c:forEach>
        </select><br>
        <input type="button" value="V�rifier la sortie" onclick="checkSortie()">
    </form>

    <div id="sortieMessage" style="display:none;">
        <p>Vous pouvez sortir du b�timent.</p>
        <button onclick="sortir()">Sortir</button>
    </div>
    
      <div id="alarme" >
        <p>Enclencher l'alarme.</p>
        <button onclick="alarme()">Alarme</button>
    </div>

</body>
<a href="index.jsp">Revenir � la page d'accueil</a>
<script>
    function checkSortie() {
        // Votre code pour v�rifier si la sortie est autoris�e
        var sortieAutorisee = true; // Remplacer par votre logique de v�rification
        
        if (sortieAutorisee) {
            // Afficher le message de sortie et le bouton
            document.getElementById("sortieMessage").style.display = "block";
        } else {
            // Afficher un message d'erreur ou effectuer une autre action si la sortie n'est pas autoris�e
            alert("La sortie n'est pas autoris�e pour le moment.");
        }
    }

    function sortir() {
        // Action � effectuer lorsque l'utilisateur choisit de sortir
        alert("Vous avez choisi de sortir du b�timent.");
        // Ajoutez ici d'autres actions si n�cessaire, par exemple, une redirection vers une page de confirmation de sortie
        var utilisateurId = document.getElementById("utilisateur").value;
        var batimentId = document.getElementById("batimentId").value;

    	window.location.href = "UpdateJournalDeBordServlet?utilisateurId=" + utilisateurId + "&batimentId=" + batimentId;
        
    }

    function alarme() {
        // Action � effectuer lorsque l'utilisateur choisit de sortir
        alert("Alarme d�clench� !");
        // Ajoutez ici d'autres actions si n�cessaire, par exemple, une redirection vers une page de confirmation de sortie
        var utilisateurId = document.getElementById("utilisateur").value;
        var batimentId = document.getElementById("batimentId").value;

    	window.location.href = "RecupererJDBAlarmeBatimentServlet?batimentId=" + batimentId;
        
    }
</script>
</html>
