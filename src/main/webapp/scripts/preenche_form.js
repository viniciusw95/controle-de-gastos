/**
 * Preenchedor de campos de formulário
 * @author Vinícius Viana
 */


class PreencheForm {
	constructor(form) {
		this.form = form;
		this.cont = 0;
	}

	newInput(classe, tipo, nome, placeholder) {
		let input = document.createElement("input");

		input.classList.add(classe);
		input.type = tipo;
		input.name = nome;
		input.id = nome;
		input.placeholder = placeholder;

		return input;
	}

	addProduto() {
		this.cont++;

		let heading = document.createElement("h2");
		heading.innerHTML = "Produto " + this.cont;
		
		let nome = this.newInput("Caixa1", "text", "nomeProduto" + this.cont, "Nome do produto: *");
		let qtd = this.newInput("Caixa1", "number", "qtdProduto" + this.cont, "Quantidade do produto: *");
		let preco = this.newInput("Caixa1", "number", "precoProduto" + this.cont, "Preço unitário: *");

		let div = document.createElement("div");
		div.id = "produto" + this.cont;
		div.name = "produto";
		div.classList.add("FrmSubItem");
		div.appendChild(heading);
		div.appendChild(nome);
		div.appendChild(qtd);
		div.appendChild(preco);
		
				
		if (this.cont === 1) {
			this.form.appendChild(div);
		} else {
			let divAnterior = document.getElementById("produto" + (this.cont-1));
			this.form.insertBefore(div, divAnterior.nextSibling);
		}
	}
}




