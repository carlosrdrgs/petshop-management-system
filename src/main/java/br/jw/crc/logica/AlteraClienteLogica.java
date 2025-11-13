package br.jw.crc.logica;

import br.jw.crc.dao.ClienteDAO;
import br.jw.crc.model.Cliente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AlteraClienteLogica implements Logica {
	/**
	 * Método recebe um ID de cliente, instancia ClienteDAO, busca o cliente 
	 * por ID e encaminha o objeto para a página
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
        long id = Long.parseLong(req.getParameter("id"));

        ClienteDAO dao = new ClienteDAO();
        Cliente cliente = dao.buscaPorId(id);

        req.setAttribute("cliente", cliente);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("altera-cliente.jsp");
        requestDispatcher.forward(req, res);
    }
}