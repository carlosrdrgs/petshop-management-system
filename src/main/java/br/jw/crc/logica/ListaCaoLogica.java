package br.jw.crc.logica;

import java.util.List;

import br.jw.crc.dao.CaoDAO;
import br.jw.crc.model.Cao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListaCaoLogica implements Logica {
	/**
	 * Método instancia CaoDAO, cria um List de todos os caes e retorna a lista de caes para serem apresentados na tela do usuário.
	 */
	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
		CaoDAO caoDAO = new CaoDAO();
		List<Cao> caes = caoDAO.getCaes();
		
		req.setAttribute("caes", caes);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("menuCaes.jsp");
		dispatcher.forward(req, res);
	}

}
