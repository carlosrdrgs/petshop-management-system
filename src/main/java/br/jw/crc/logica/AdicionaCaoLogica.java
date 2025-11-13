package br.jw.crc.logica;

import br.jw.crc.dao.CaoDAO;
import br.jw.crc.dao.ClienteDAO;
import br.jw.crc.model.Cao;
import br.jw.crc.model.Cliente;
import br.jw.crc.model.Porte;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdicionaCaoLogica implements Logica {
	/**
	 * Método recebe parametros da classe Cao, instancia cao, 
	 * adiciona no banco de dados, retorna res com sucesso.
	 */
	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Cao cao = new Cao();
		
		cao.setNome(req.getParameter("nome"));
		cao.setRaca(req.getParameter("raca"));
		cao.setPorte(Porte.valueOf(req.getParameter("porte").toUpperCase()));
		ClienteDAO clienteDAO = new ClienteDAO();
		Long id = clienteDAO.buscaIdPorCpf(req.getParameter("cpf"));
		
		CaoDAO caoDAO = new CaoDAO();
		caoDAO.adiciona(cao,id);
		
		res.sendRedirect("controladora?logica=ListaCaoLogica");
	}

}
