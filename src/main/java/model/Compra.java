package model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Compra {
	
	private int idCompra;
	private String diaCompra;
	private String localCompra;
	private BigDecimal precoCompra;
	
	private ArrayList<Produto> produtos = null;
	
	public Compra() {
		produtos = new ArrayList<>();
	}
	
	
	public Compra(int idCompra, String diaCompra, String localCompra, BigDecimal precoCompra) {
		this();
		this.idCompra = idCompra;
		this.diaCompra = diaCompra;
		this.localCompra = localCompra;
		this.precoCompra = precoCompra;
	}


	public int getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}

	public String getDiaCompra() {
		return diaCompra;
	}

	public void setDiaCompra(String diaCompra) {
		this.diaCompra = diaCompra;
	}

	public String getLocalCompra() {
		return localCompra;
	}

	public void setLocalCompra(String localCompra) {
		this.localCompra = localCompra;
	}

	public BigDecimal getPrecoCompra() {
		return precoCompra;
	}

	public void setPrecoCompra() {
		BigDecimal precoCompraFinal = new BigDecimal("0.0");
		for (Produto produto: produtos) {
			precoCompraFinal = precoCompraFinal.add(produto.getPrecoFinal());
		}
		this.precoCompra = precoCompraFinal;
	}

	public void adicionarProduto(Produto produto) {
		produtos.add(produto);
	}
	public ArrayList<Produto> getProdutos() {
		return produtos;
	}
	
}
