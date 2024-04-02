<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Créer un bâtiment</title>
</head>
<body>
    <h2>Créer un bâtiment</h2>
    <form action="CreateBatimentServlet">
        <label for="nom">Nom du bâtiment:</label><br>
        <input type="text" id="nomBat" name="nomBat"><br><br>
        
        <!-- Ajoutez d'autres champs pour d'autres informations sur le bâtiment si nécessaire -->
        
        <h3>Autorisations d'accès par type d'utilisateur:</h3>
        <label for="typeAdmin">Enseignant:</label>
        <input type="checkbox" id="typeEnseignant" name="typeEnseignant" value="true">
        <br>
        <label for="typeUtilisateur">Personnel Administratif:</label>
        <input type="checkbox" id="typePersonnelAdministratif" name="typePersonnelAdministratif" value="true">
        <br>
        <label for="typeAdmin">Etudiant Info:</label>
        <input type="checkbox" id="typeEtudiantInfo" name="typeEtudiantInfo" value="true">
        <br>
        <label for="typeUtilisateur">Etudiant Staps:</label>
        <input type="checkbox" id="typeEtudiantStaps" name="typeEtudiantStaps" value="true">
        <br><br>
        
        <input type="submit" value="Créer">
    </form>
    
    <a href="index.jsp">Revenir à la page d'accueil</a>
</body>
</html>