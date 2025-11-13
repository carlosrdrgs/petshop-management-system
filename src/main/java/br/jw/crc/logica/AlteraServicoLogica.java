package br.jw.crc.logica;

import br.jw.crc.dao.ServicoDAO;
import br.jw.crc.model.Servico;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AlteraServicoLogica implements Logica {
	/**
	 * Método recebe um ID, instancia ServicoDAO, busca por ID o serviço
	 * e encaminha o objeto para a página para edição.
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));

        ServicoDAO dao = new ServicoDAO();
        Servico servico = dao.buscaPorId(id);

        req.setAttribute("servico", servico);


        RequestDispatcher rd = req.getRequestDispatcher("altera-servico.jsp");
        rd.forward(req, res);
    }
}