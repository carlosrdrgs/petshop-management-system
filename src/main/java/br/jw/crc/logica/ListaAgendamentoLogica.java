package br.jw.crc.logica;

import java.time.LocalDate;
import java.util.List;
import br.jw.crc.dao.AgendamentoDAO;
import br.jw.crc.model.Agendamento;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListaAgendamentoLogica implements Logica {
	/**
	 * Método instancia AgendamentoDAO, e faz uma lista de todos os agendamentos pendentes até a data de hoje para serem apresentados na tela do usuário.
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        LocalDate hoje = LocalDate.now(); 

        AgendamentoDAO dao = new AgendamentoDAO();
        List<Agendamento> agendamentos = dao.listarPendentes(hoje);

        req.setAttribute("agendamentosPendentes", agendamentos);

        RequestDispatcher rd = req.getRequestDispatcher("/lista-agendamento.jsp"); 
        rd.forward(req, res);
    }
}