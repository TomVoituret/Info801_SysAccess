<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Liste des Journaux de Bord</title>
</head>
<body>

<h2>Liste des Journaux de Bord</h2>

<c:forEach var="journal" items="${listeJournaux}" varStatus="loop">
    <c:if test="${loop.first || !journal.batiment.id.equals(listeJournaux[loop.index - 1].batiment.id)}">
        <h3>${journal.batiment.nom}</h3>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Heure d'Entrée</th>
                    <th>Heure de Sortie</th>
                    <th>Utilisateur</th>
                </tr>
            </thead>
            <tbody>
    </c:if>
    <tr>
        <td>${journal.id}</td>
        <td>${journal.heureEntree}</td>
        <td>${journal.heureSortie}</td>
        <td>${journal.utilisateur.nom} ${journal.utilisateur.prenom}</td>
    </tr>
    <c:if test="${loop.last || !journal.batiment.id.equals(listeJournaux[loop.index + 1].batiment.id)}">
            </tbody>
        </table>
    </c:if>
</c:forEach>
<a href="index.jsp">Revenir à la page d'accueil</a>
</body>
</html>
