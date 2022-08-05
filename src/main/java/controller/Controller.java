package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Compra;
import model.DAO;
import model.Produto;
import model.Usuario;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/validar-login" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	Usuario usuario = null;

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		//PrintWriter out = response.getWriter();

		String action = request.getServletPath();
		
		if (action.equals("/main")) {
			compras(request, response);
		} else if (action.equals("/validar-login")) {
			usuario = novoUsuario(request, response);
			
			String result = dao.validarLogin(usuario);
			if (result.equals("OK")) {
				response.sendRedirect("main");
			} else {
				response.sendRedirect("index.jsp?erroLogin=" + result);
			}
		} else if (action.equals("/insert")) {
			Compra compra = novaCompra(request, response);
			adicionarProdutos(compra, request, response);
			compra.setPrecoCompra();
			dao.cadastrarCompra(compra, usuario);
			dao.cadastrarProdutos(compra);
			dao.cadastrarProdutosComprados(compra);
			response.sendRedirect("main");
		}
	}
	
	// Listar compras
	protected void compras(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Compra> compras = dao.listarCompras();
		/*for (int i = 0; i < compras.size(); i++) {
			System.out.print("Compra ");
			System.out.println(compras.get(i).getIdCompra() + ": ");
			System.out.println("Dia da compra: " + compras.get(i).getDiaCompra());
			System.out.println("Local da compra: " + compras.get(i).getLocalCompra());
			System.out.println("Preço da compra " + compras.get(i).getPrecoCompra());
			System.out.println();
			System.out.println("Produtos da compra " + compras.get(i).getIdCompra() + ": ");
			for (Produto produto: compras.get(i).getProdutos()) {
				System.out.println("Id do produto: " + produto.getIdProduto());
				System.out.println("Nome do produto: " + produto.getNomeProduto());
				System.out.println("Qtd do produto: " + produto.getQtdProduto());
				System.out.println("Preço do produto: " + produto.getPrecoProduto());
				System.out.println();
			}
		}
		*/
		request.setAttribute("compras", compras);
		RequestDispatcher rd = request.getRequestDispatcher("controle-gastos.jsp");
		rd.forward(request, response);
		//response.sendRedirect("controle-gastos.jsp");

	}
	
	protected Usuario novoUsuario (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Usuario usuario = new Usuario();
		usuario.setLogin(request.getParameter("loginUsuario"));
		usuario.setSenha(request.getParameter("senhaUsuario"));
		return usuario;
	}
	protected Compra novaCompra(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Compra compra = new Compra();
		compra.setIdCompra(dao.getRowCount("id_compra", "compras") + 1);
		compra.setDiaCompra(request.getParameter("diaCompra"));
		compra.setLocalCompra(request.getParameter("localCompra"));
		return compra;
	}
	protected void adicionarProdutos(Compra compra, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int i = 1;
		do {
			Produto prod = new Produto();
			prod.setNomeProduto(request.getParameter("nomeProduto" + i));
			prod.setQtdProduto(request.getParameter("qtdProduto" + i));
			prod.setPrecoProduto(request.getParameter("precoProduto" + i));
			compra.adicionarProduto(prod);
			i++;
		} while (request.getParameter("nomeProduto" + i) != null);
		
	}

}
