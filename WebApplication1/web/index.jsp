<%-- 
    Document   : test.jsp
    Created on : 12 déc. 2023, 06:25:02
    Author     : Sleep_Boo
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Simple Form</title>
</head>
<body>

    <h2>Simple Form</h2>

    <a href="ajouterActivite.jsp"> vers ajouter activite </a>
    <a href="ajouterBouquet.jsp"> vers ajouter bouquet </a>
    <a href="ajouterCategorieLieu.jsp"> vers ajouter categorie lieu </a>
    <a href="ajouterLieu.jsp"> vers ajouter lieu </a>

    <form action="MyServlet" method="post">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
        <br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        <br>

        <input type="submit" value="Submit">
    </form>

</body>
</html>
