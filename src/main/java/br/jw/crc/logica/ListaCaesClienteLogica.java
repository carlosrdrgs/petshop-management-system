package br.jw.crc.logica;

import java.util.List;

import br.jw.crc.dao.CaoDAO;
import br.jw.crc.model.Cao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListaCaesClienteLogica implements Logica {
	/**
	 * Método instancia ServicoDAO, recebe um ID de cliente, cria um List de todos os caes deste cliente e retorna a lista de caes para serem apresentados na tela do usuário.
	 */
	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
		CaoDAO caoDAO = new CaoDAO();
		List<Cao> caes = caoDAO.getCaesPorClienteId(Long.parseLong(req.getParameter("id")));
		
		req.setAttribute("caes", caes);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("menuCaes.jsp");
		dispatcher.forward(req, res);
	}

}
