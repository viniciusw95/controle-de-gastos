/**
 * 
 */
 
function validaCompra() {
	var htmlIDs = ["nomeProduto", "qtdProduto", "precoAtual"];
	let errors = "";
	var element;
	
	for (id of htmlIDs) {
		element = document.getElementById(id); 
		if (element.value === "") {
			errors += "\n - " + element.placeholder + " não pode ser vazio";
		}
	}
	if (errors === "") {
		document.location.href = "cadastrar-compra";
	} else {
		alert("Verifique o(s) erro(s): " + errors);
	}
}