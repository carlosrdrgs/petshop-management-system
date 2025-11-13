package br.jw.crc.logica;

import java.util.List;
import br.jw.crc.dao.AgendamentoDAO;
import br.jw.crc.dao.CaoDAO;
import br.jw.crc.model.Agendamento;
import br.jw.crc.model.Cao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HistoricoCaoLogica implements Logica {
	/**
	 * Método recebe um ID (CAao = caoId), instancia caoDAO e Cao (buscando pelo ID de cão), realiza a busca de todos os agendamentos ja realizados pelo cao e armazena
	 * em uma lista, retorna a lista para ser exibida na tela do usuário.
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {

        int id = Integer.parseInt(req.getParameter("caoId"));

        CaoDAO caoDao = new CaoDAO();
        Cao cao = caoDao.buscaPorId(id);

        AgendamentoDAO agendamentoDao = new AgendamentoDAO();
        List<Agendamento> historico = agendamentoDao.listarHistoricoPorCaoId(id);

        req.setAttribute("cao", cao);
        req.setAttribute("historicoAgendamentos", historico);

        RequestDispatcher rd = req.getRequestDispatcher("/historico-cao.jsp");
        rd.forward(req, res);
    }
}