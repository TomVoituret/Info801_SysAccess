<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Supprimer un utilisateur</title>
</head>
<body>
<h2>Liste des Utilisateurs :</h2>
<table>
    <tr>
        <th>Id</th>
        <th>Date d'entrée</th>
        <th>Date de sortie</th>
        <th>Action</th> <!-- Ajout d'une colonne pour l'action -->
    </tr>
    <c:forEach items="${utilisateurs}" var="utilisateur">
        <tr>
            <td>${ticket.id}</td>
            <td><fmt:formatDate value="${ticket.entryDate}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
            <td><fmt:formatDate value="${ticket.exitDate}" pattern="dd/MM/yyyy HH:mm:ss" /></td>
            <td><button onclick="deleteUtilisateur(${utilisateur.id})">Supprimer l'utilisateur</button></td>
        </tr>
    </c:forEach>
</table>
<form id="refreshForm" method="get" action="UpdateUtilisateursServlet">
    <input type="submit" name="valider" value="Actualiser">
</form>
<a href="index.jsp">Revenir à la page d'accueil</a>

<!-- Script pour la fonction de suppression -->
<script>
    function deleteUtilisateur(utilisateurId) {
        
    }
</script>

</body>
</html>