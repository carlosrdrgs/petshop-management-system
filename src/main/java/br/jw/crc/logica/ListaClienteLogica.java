package br.jw.crc.logica;

import java.util.List;

import br.jw.crc.dao.ClienteDAO;
import br.jw.crc.model.Cliente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListaClienteLogica implements Logica {
	/**
	 * Método instancia ClienteDAO, cria um List de todos os clientes e retorna a lista de clientes para serem apresentados na tela do usuário.
	 */
	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ClienteDAO clienteDAO = new ClienteDAO();
		List<Cliente> clientes = clienteDAO.getClientes();
		
		req.setAttribute("clientes", clientes);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("menuClientes.jsp");
		dispatcher.forward(req, res);
	}

}
