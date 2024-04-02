<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cr�er un utilisateur</title>
</head>
<body>
    <h2>Cr�er un nouvel utilisateur</h2>
    <form action="CreateUtilisateurServlet">
        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nom" required><br><br>
        
        <label for="prenom">Pr�nom :</label>
        <input type="text" id="prenom" name="prenom" required><br><br>
        
        <label for="type">Type :</label>
        <select id="type" name="type" required>
            <option value="Enseignant">Enseignant</option>
            <option value="PersonnelAdministratif">Personnel Administratif</option>
            <option value="EtudiantInfo">Etudiant Info</option>
            <option value="EtudiantStaps">Etudiant Staps</option>
            <!-- Ajoutez d'autres options au besoin -->
        </select><br><br>
        
        <input type="submit" value="Cr�er utilisateur">
    </form>
    
    <a href="index.jsp">Revenir � la page d'accueil</a>
</body>
</html>
