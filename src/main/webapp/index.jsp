<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Controle de gastos</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>Meus Gastos App</h1>
	
	<% 
		String erroLogin = request.getParameter("erroLogin");
		
		if (erroLogin != null) { 	
			switch (erroLogin) {
				case "loginInexistente" : {
					out.println("<script>alert(\"Este login não existe no sistema\")</script>");
					break;
				}
				case "senhaIncorreta" : {
					out.println("<script>alert(\"A senha digitada está incorreta\")</script>");
					break;
				}
			}	
		} 
	%>
	
	<form name="frmLogin" action="validar-login" method="get">
		<label for="loginUsuario">Login: </label>
		<input id="loginUsuario" name="loginUsuario" type="text" placeholder="Digite aqui o usuário">
		
		<label for="senhaUsuario">Senha: </label>
		<input id="senhaUsuario" name="senhaUsuario" type="password" placeholder="Digite aqui o senha">
		
		<input type="button" class="Botao1" onclick="validarLogin();" value="Acessar aplicativo">
	</form>
	<script src="scripts/validador.js"></script>
</body>
</html>