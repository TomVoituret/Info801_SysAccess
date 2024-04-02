<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
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
        <th>Nom</th>
        <th>Prénom</th>
        <th>Type</th>
        <th>Action</th> <!-- Ajout d'une colonne pour l'action -->
    </tr>
    <c:forEach items="${utilisateurs}" var="utilisateur">
        <tr>
            <td>${utilisateur.id}</td>
            <td>${utilisateur.nom}</td>
            <td>${utilisateur.prenom}</td>
            <td>${utilisateur.type}</td>
            <td><button onclick="deleteUtilisateur(${utilisateur.id})">Supprimer l'utilisateur</button></td>
        </tr>
    </c:forEach>
</table>
<form id="refreshForm" method="get" action="GetUserDeleteServlet">
    <input type="submit" name="valider" value="Actualiser">
</form>
<a href="index.jsp">Revenir à la page d'accueil</a>

<!-- Script pour la fonction de suppression -->
<script>
function deleteUtilisateur(utilisateurId) {
    // Appeler la servlet DeleteUtilisateurServlet avec l'ID de l'utilisateur à supprimer
    var url = "DeleteUtilisateurServlet?id=" + utilisateurId;
    window.location.href = url;
}

</script>

</body>
</html>