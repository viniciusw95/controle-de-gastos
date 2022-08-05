package model;

import java.math.BigDecimal;

public class Produto {
	private int idProduto;
	private String nomeProduto;
	private String qtdProduto;
	private BigDecimal precoProduto;
	
	public Produto() {
		super();
	}
	public Produto(int idProduto, String nomeProduto, String qtdProduto, String precoProduto) {
		super();
		this.idProduto = idProduto;
		this.nomeProduto = nomeProduto;
		this.qtdProduto = qtdProduto;
		this.precoProduto = new BigDecimal(precoProduto);
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public String getQtdProduto() {
		return qtdProduto;
	}
	public void setQtdProduto(String qtdProduto) {
		this.qtdProduto = qtdProduto;
	}
	public BigDecimal getPrecoProduto() {
		return precoProduto;
	}
	public BigDecimal getPrecoFinal() {
		return precoProduto.multiply(new BigDecimal(getQtdProduto()));
	}
	public void setPrecoProduto(String precoProduto) {
		this.precoProduto = new BigDecimal(precoProduto);
	}
	public int getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}
	
}
