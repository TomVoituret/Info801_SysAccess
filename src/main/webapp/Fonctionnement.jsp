<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>V�rification d'acc�s</title>
</head>
<body>
    <h2>V�rification d'acc�s</h2>
    
    <form id="checkAccessForm">
        <label for="utilisateur">S�lectionnez un utilisateur :</label>
        <select id="utilisateur" name="utilisateur">
            <c:forEach items="${utilisateurs}" var="utilisateur">
                <option value="${utilisateur.id}">${utilisateur.nom} ${utilisateur.prenom}</option>
            </c:forEach>
        </select><br>
        
        <label for="batiment">S�lectionnez un b�timent :</label>
        <select id="batiment" name="batiment">
            <c:forEach items="${batiments}" var="batiment">
                <option value="${batiment.id}">${batiment.nom}</option>
            </c:forEach>
        </select><br>
        
        <input type="submit" value="V�rifier l'acc�s">
    </form>

    <form id="BatimentInfo">
        <input type="submit" name="BatimentInfo" value="Regarder Batiment courant">
    </form>

    <c:if test="${!empty acces}">
        <h3>R�sultat de la v�rification :</h3>
        <p>
            Pour l'utilisateur s�lectionn�, l'acc�s au b�timent est :
            <span id="accessStatus">
                <c:choose>
                    <c:when test="${acces eq true}">ouvert</c:when>
                    <c:when test="${acces eq false}">ferm�</c:when>
                    <c:otherwise>ind�termin�</c:otherwise>
                </c:choose>
            </span>
        </p>
    </c:if>
<a href="index.jsp">Revenir � la page d'accueil</a>
</body>
<script>
    var acces = ${acces}; // D�claration de la variable JavaScript
    var enterButtonClicked = ${dejaCliquer}; // Variable pour suivre si le bouton "Entrer" a �t� cliqu�

    // Fonction pour g�rer l'affichage en fonction de la valeur de acces
    function handleAccessStatus(acces) {
        var resultMessage = document.getElementById("accessStatus");
        
        if (acces === true) {
            // Afficher le message de succ�s
            resultMessage.style.color = "green";
            resultMessage.innerText = "ouvert";

            resultMessage.appendChild(document.createElement("br"));
            
            // Affichage de la dur�e de l'�ch�ance (30 secondes)
            setTimeout(function() {
                resultMessage.style.color = "black";
            }, 5000); // 5 secondes
            setTimeout(function() {
                resultMessage.innerText = "";
            }, 25000); // 25 secondes

            
            // Affichage du bouton "Entrer" pendant l'�ch�ance
            var enterButton = document.createElement("input");
            enterButton.type = "button";
            enterButton.value = "Entrer";
            enterButton.id = "enterButton";
            enterButton.addEventListener("click", function() {
                if(enterButtonClicked){
                	alert("Plus d'une personne est entr�e avec le badge, Alarme d�clench�e !");
                    } else {
                    	enterButtonClicked = true;
                    	window.location.href = "CreateJournalDeBordServlet?currentUserId=" + ${currentUserId} + "&currentBatId=" + ${currentBatId};
                        }
                
            });


            
            resultMessage.appendChild(enterButton);
        } else if (acces === false) {
            // Afficher le message d'�chec
            resultMessage.style.color = "red";
            resultMessage.innerText = "ferm�";
            
            // Affichage de la dur�e de l'�ch�ance (10 secondes)
            setTimeout(function() {
                resultMessage.innerText = "";
                resultMessage.style.color = "black";
            }, 10000); // 10 secondes
        } else {
            // Si la valeur de acces n'est pas d�finie ou ind�termin�e
            resultMessage.style.color = "black";
            resultMessage.innerText = "";
        }
    }

    // Appel de la fonction pour g�rer l'affichage en fonction de la valeur de acces
    handleAccessStatus(acces);

    // Fonction appel�e lors de la soumission du formulaire de v�rification d'acc�s
    document.getElementById("checkAccessForm").addEventListener("submit", function(event) {
        event.preventDefault(); // Emp�che la soumission normale du formulaire
        
        // R�cup�rer les valeurs s�lectionn�es dans les listes d�roulantes
        var selectedUtilisateurId = document.getElementById("utilisateur").value;
        var selectedBatimentId = document.getElementById("batiment").value;
        
        // Appeler le servlet avec les param�tres n�cessaires
        window.location.href = "CheckAccessServlet?utilisateurId=" + selectedUtilisateurId + "&batimentId=" + selectedBatimentId;
    });
    
    // Fonction appel�e lors du clic sur le bouton "Regarder Batiment courant"
    document.getElementById("BatimentInfo").addEventListener("click", function(event) {
        event.preventDefault(); // Emp�che le comportement par d�faut du bouton
        
        var selectedBatimentId = document.getElementById("batiment").value;
        
        // Appeler le servlet avec les param�tres n�cessaires
        window.location.href = "GetInfoForSortieServlet?id=" + selectedBatimentId;
        
    });

</script>
</html>
