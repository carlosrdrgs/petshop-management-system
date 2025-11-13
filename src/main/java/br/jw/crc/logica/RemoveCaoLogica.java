package br.jw.crc.logica;

import br.jw.crc.dao.CaoDAO;
import br.jw.crc.dao.ClienteDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RemoveCaoLogica implements Logica {
	/**
	 * Método recebe parametro de ID, e faz solicitação para DAO deletar do banco de dados
	 */
	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String id = req.getParameter("id");
		CaoDAO caoDAO = new CaoDAO();
		caoDAO.remove(Integer.parseInt(id));
		res.sendRedirect("controladora?logica=ListaCaoLogica");
	}

}
