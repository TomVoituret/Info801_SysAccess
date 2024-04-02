<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vérification d'accès</title>
</head>
<body>
    <h2>Vérification d'accès</h2>
    
    <form id="checkAccessForm">
        <label for="utilisateur">Sélectionnez un utilisateur :</label>
        <select id="utilisateur" name="utilisateur">
            <c:forEach items="${utilisateurs}" var="utilisateur">
                <option value="${utilisateur.id}">${utilisateur.nom} ${utilisateur.prenom}</option>
            </c:forEach>
        </select><br>
        
        <label for="batiment">Sélectionnez un bâtiment :</label>
        <select id="batiment" name="batiment">
            <c:forEach items="${batiments}" var="batiment">
                <option value="${batiment.id}">${batiment.nom}</option>
            </c:forEach>
        </select><br>
        
        <input type="submit" value="Vérifier l'accès">
    </form>

    <form id="BatimentInfo">
        <input type="submit" name="BatimentInfo" value="Regarder Batiment courant">
    </form>

    <c:if test="${!empty acces}">
        <h3>Résultat de la vérification :</h3>
        <p>
            Pour l'utilisateur sélectionné, l'accès au bâtiment est :
            <span id="accessStatus">
                <c:choose>
                    <c:when test="${acces eq true}">ouvert</c:when>
                    <c:when test="${acces eq false}">fermé</c:when>
                    <c:otherwise>indéterminé</c:otherwise>
                </c:choose>
            </span>
        </p>
    </c:if>
<a href="index.jsp">Revenir à la page d'accueil</a>
</body>
<script>
    var acces = ${acces}; // Déclaration de la variable JavaScript
    var enterButtonClicked = ${dejaCliquer}; // Variable pour suivre si le bouton "Entrer" a été cliqué

    // Fonction pour gérer l'affichage en fonction de la valeur de acces
    function handleAccessStatus(acces) {
        var resultMessage = document.getElementById("accessStatus");
        
        if (acces === true) {
            // Afficher le message de succès
            resultMessage.style.color = "green";
            resultMessage.innerText = "ouvert";

            resultMessage.appendChild(document.createElement("br"));
            
            // Affichage de la durée de l'échéance (30 secondes)
            setTimeout(function() {
                resultMessage.style.color = "black";
            }, 5000); // 5 secondes
            setTimeout(function() {
                resultMessage.innerText = "";
            }, 25000); // 25 secondes

            
            // Affichage du bouton "Entrer" pendant l'échéance
            var enterButton = document.createElement("input");
            enterButton.type = "button";
            enterButton.value = "Entrer";
            enterButton.id = "enterButton";
            enterButton.addEventListener("click", function() {
                if(enterButtonClicked){
                	alert("Plus d'une personne est entrée avec le badge, Alarme déclenchée !");
                    } else {
                    	enterButtonClicked = true;
                    	window.location.href = "CreateJournalDeBordServlet?currentUserId=" + ${currentUserId} + "&currentBatId=" + ${currentBatId};
                        }
                
            });


            
            resultMessage.appendChild(enterButton);
        } else if (acces === false) {
            // Afficher le message d'échec
            resultMessage.style.color = "red";
            resultMessage.innerText = "fermé";
            
            // Affichage de la durée de l'échéance (10 secondes)
            setTimeout(function() {
                resultMessage.innerText = "";
                resultMessage.style.color = "black";
            }, 10000); // 10 secondes
        } else {
            // Si la valeur de acces n'est pas définie ou indéterminée
            resultMessage.style.color = "black";
            resultMessage.innerText = "";
        }
    }

    // Appel de la fonction pour gérer l'affichage en fonction de la valeur de acces
    handleAccessStatus(acces);

    // Fonction appelée lors de la soumission du formulaire de vérification d'accès
    document.getElementById("checkAccessForm").addEventListener("submit", function(event) {
        event.preventDefault(); // Empêche la soumission normale du formulaire
        
        // Récupérer les valeurs sélectionnées dans les listes déroulantes
        var selectedUtilisateurId = document.getElementById("utilisateur").value;
        var selectedBatimentId = document.getElementById("batiment").value;
        
        // Appeler le servlet avec les paramètres nécessaires
        window.location.href = "CheckAccessServlet?utilisateurId=" + selectedUtilisateurId + "&batimentId=" + selectedBatimentId;
    });
    
    // Fonction appelée lors du clic sur le bouton "Regarder Batiment courant"
    document.getElementById("BatimentInfo").addEventListener("click", function(event) {
        event.preventDefault(); // Empêche le comportement par défaut du bouton
        
        var selectedBatimentId = document.getElementById("batiment").value;
        
        // Appeler le servlet avec les paramètres nécessaires
        window.location.href = "GetInfoForSortieServlet?id=" + selectedBatimentId;
        
    });

</script>
</html>
