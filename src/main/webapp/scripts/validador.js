/**
 * Validação de formulário
 * @author Vinícius Viana
 */
 
function validar() {	
	let erros = "";
	
	let diaCompra = document.getElementById("diaCompra");
	
		
	i = 1;
	while (document.getElementById("produto"+i) !== null) {
		let nome = document.getElementById("nomeProduto"+i);
		let qtd = document.getElementById("qtdProduto"+i);
		let preco = document.getElementById("precoProduto"+i);
		
		if (nome.value === "") {
			erros += "\nProduto " + i + ":\n";
		}
		
		let produto = [nome, qtd, preco];
		for (atributo of produto) {
			if (atributo.value === "") {
				erros += " - " + atributo.placeholder + " deve ser preenchido\n";
			}
		}
		i++;
	}
	
	if (erros !== "") {
		alert("Verifique os erros: \n" + erros);
	} else {
		document.forms["frmCadastrarCompra"].submit();
	}	
}

function validarLogin() {
	let usuario = document.getElementById("loginUsuario").value;
	let senha = document.getElementById("senhaUsuario").value;
	
	if (usuario !== "" & senha !== "") {
		document.forms["frmLogin"].submit();
	} else {
		alert("Atenção: Verifique usuário e senha");
	}
	
	
}