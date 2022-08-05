<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList,model.Compra, model.Produto"%>
<%ArrayList<Compra> compras = (ArrayList<Compra>) request.getAttribute("compras");%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Controle de gastos</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>Controle de gastos</h1>

	<a href="cadastrar-compra.html" class="Botao1">Novo cadastro de
		compra</a>
	
	<% for (Compra compra: compras) { %>
	<table>
		
		<thead>
			<tr>
				<th>Id da compra</th>
				<th>Dia</th>
				<th colspan="2">Local</th>	
				<th>Preço da compra (R$)</th>
			</tr>
		</thead>
		<tbody>
			
				<tr>
					<td rowspan="0">Compra <%=compra.getIdCompra() %></td>
					<td><%=compra.getDiaCompra() %></td>
					<td colspan="2"><%=compra.getLocalCompra() %></td>
					<td><%=compra.getPrecoCompra() %></td>
				</tr>	
				
				<tr>
					<th>Nome do produto:</th>
					<th>Qtd:</th>
					<th>Preço unidade (R$)</th>
					<th>Preço final   (R$)</th>
				</tr>
				
				<% for (Produto produto : compra.getProdutos()) { %>
					<tr>
						<td><%=produto.getNomeProduto() %></td>
						<td><%=produto.getQtdProduto() %></td>
						<td><%=produto.getPrecoProduto() %></td>
						<td><%=produto.getPrecoFinal() %></td>
					</tr>
				<% } %>	
			<% } %>
		</tbody>
	</table>


</body>
</html>