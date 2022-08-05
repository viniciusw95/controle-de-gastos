package model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DAO {
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String user = "root";
	private String password = "MYBIA36";
	private String url = "jdbc:mysql://localhost/controle_gastos?useTimezone=true&serverTimezone=UTC";

	public Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public String validarLogin(Usuario usuario) {
		// String login = usuario.getLogin();
		// String senha = usuario.getLogin();
		return "OK";
	}

	/* CRUD READ */
	public ArrayList<Compra> listarCompras() {
		String queryCompras = "select * from compras order by id_compra;";
		String queryProdutos = "select id_compra, produtos.id_produto, produtos.nome_produto, \n"
				+ "	qtd_produto, preco_produto\n"
				+ "from produtos \n"
				+ "inner join produtos_comprados\n"
				+ "on produtos.id_produto = produtos_comprados.id_produto\n"
				+ "order by produtos_comprados.id_compra, produtos.nome_produto;";
		Map<Integer, Compra> mapCompras = new HashMap<>();
		ArrayList<Compra> compras = new ArrayList<>();
		try {
			Connection con = conectar();
			PreparedStatement pstCompras = con.prepareStatement(queryCompras);
			ResultSet rsCompras = pstCompras.executeQuery();
			
			// Criando as compras
			while (rsCompras.next()) {
				int idCompra = rsCompras.getInt(1);
				String diaCompra = rsCompras.getString(3);
				String localCompra = rsCompras.getString(4);
				BigDecimal precoCompra = rsCompras.getBigDecimal(5);
				Compra compra = new Compra(idCompra, diaCompra, localCompra, precoCompra);
				mapCompras.put(idCompra, compra);
				compras.add(compra);
			}
			// Preenchendo compras com produtos
			PreparedStatement pstProdutos = con.prepareStatement(queryProdutos);
			ResultSet rsProdutos = pstProdutos.executeQuery();
			
			while (rsProdutos.next()) {
				int idProduto = rsProdutos.getInt("id_produto");
				String nomeProduto = rsProdutos.getString("nome_produto");
				String qtdProduto = rsProdutos.getString("qtd_produto");
				String precoProduto = rsProdutos.getString("preco_produto");
				Produto produto = new Produto(idProduto, nomeProduto, qtdProduto, precoProduto);
				
				int idCompra = rsProdutos.getInt("id_compra");
				mapCompras.get(idCompra).adicionarProduto(produto);
			}
			
			con.close();
			return compras;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	
	public void cadastrarCompra(Compra compra, Usuario usuario) {
		String createCompra = "insert into compras (id_compra, id_usuario, dia_compra, local_compra, preco_compra) "
				+ "values (?, ?, ?, ?, ?);";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(createCompra);
			pst.setInt(1, compra.getIdCompra());
			pst.setInt(2, usuario.getIdUsuario());
			pst.setString(3, compra.getDiaCompra());
			pst.setString(4, compra.getLocalCompra());
			pst.setBigDecimal(5, compra.getPrecoCompra());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println("Erro em 'cadastrarCompra()'");
			System.out.println(e);
		}
	}

	public void cadastrarProdutos(Compra compra) {

		try {
			for (Produto produto : compra.getProdutos()) {
				if (!isProdutoCadastrado(produto)) {
					cadastrarProduto(produto);
				}
			}
		} catch (Exception e) {
			System.out.println("Erro em 'cadastrarProdutos()'");
			System.out.println(e);
		}
	}

	public int getRowCount(String coluna, String tabela) {
		try {
			Connection con = conectar();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(String.format("SELECT COUNT(%s) FROM %s;", coluna, tabela));
			rs.next();
			int counter = rs.getInt(String.format("COUNT(%s)", coluna));
			con.close();
			return counter;
		} catch (Exception e) {
			System.out.println("Erro em 'getRowCount()'");
			System.out.println(e);
			return -1;
		}
	}

	private boolean isProdutoCadastrado(Produto produto) {
		String count = "select count(nome_produto) from produtos where nome_produto = ?;";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(count);
			pst.setString(1, produto.getNomeProduto());
			ResultSet rs = pst.executeQuery();
			rs.next();
			int cnt = rs.getInt("count(nome_produto)");
			con.close();
			return cnt == 1;
		} catch (Exception e) {
			System.out.println("Erro em 'isProdutoCadastrado()'");
			System.out.println(e);
			return false;
		}
	}

	private void cadastrarProduto(Produto produto) {
		String create = "insert into produtos (nome_produto) values (?);";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, produto.getNomeProduto());
			int rowsInserted = pst.executeUpdate();
			con.close();
			if (rowsInserted != 1) {
				throw new Exception("Erro: Produto " + produto.getNomeProduto() + " não cadastrado...");
			}
		} catch (Exception e) {
			System.out.println("Erro em 'cadastrarProduto()'");
			System.out.println(e);
		}
	}
	
	public void cadastrarProdutosComprados(Compra compra) {
	  	for (Produto produto : compra.getProdutos()) {
			produto.setIdProduto(getIdProduto(produto));
			cadastrarProdutoComprado(produto, compra);
		}
	}

	private void cadastrarProdutoComprado(Produto produto, Compra compra) {
		String create = "insert into produtos_comprados (id_compra, id_produto, qtd_produto, preco_produto) "
				+ "values (?, ?, ?, ?);";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(create);
			
			pst.setInt(1, compra.getIdCompra());
			pst.setInt(2, produto.getIdProduto());
			pst.setString(3, produto.getQtdProduto());
			pst.setBigDecimal(4, produto.getPrecoProduto());
			
			int rowsInserted = pst.executeUpdate();
			con.close();
			if (rowsInserted != 1) {
				throw new Exception("Erro: Produto comprado '" + produto.getNomeProduto() + "' não foi cadastrado...");
			}
		} catch (Exception e) {
			System.out.println("Erro em 'cadastrarProdutoComprado()'");
			System.out.println(e);
		}
	}

	private int getIdProduto(Produto produto) {
		try {
			Connection con = conectar();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(String.format(
					"SELECT id_produto FROM produtos " + "WHERE nome_produto = '%s';", produto.getNomeProduto()));
			if (rs.next()) {
				int idProduto = rs.getInt("id_produto");
				con.close();
				return idProduto;
			} else {
				throw new Exception("Produto não encontrado...");
			}

		} catch (Exception e) {
			System.out.println("Erro em 'getIdProduto()'");
			System.out.println(e);
			return -1;

		}
	}

}
