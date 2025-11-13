package br.jw.crc.logica;

import br.jw.crc.dao.CaoDAO;
import br.jw.crc.model.Cao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AlteraCaoLogica implements Logica {
	/**
	 * Método recebe um ID de cão, instancia CaoDAO, busca o cão
	 * por ID e encaminha o objeto para a página
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
        long id = Long.parseLong(req.getParameter("id"));

        CaoDAO dao = new CaoDAO();
        Cao cao = dao.buscaPorId(id);

        req.setAttribute("cao", cao);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("altera-cao.jsp");
        requestDispatcher.forward(req, res);
    }
}