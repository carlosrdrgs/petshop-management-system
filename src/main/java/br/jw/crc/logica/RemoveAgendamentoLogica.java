package br.jw.crc.logica;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import br.jw.crc.dao.AgendamentoDAO;
import br.jw.crc.model.Agendamento;
import br.jw.crc.model.Status;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RemoveAgendamentoLogica implements Logica {

	/**
	 * Método recebe parametro de ID, busca pelos dados do agendamento no DAO, retorna res se foi cancelado ou caso retorna mensagem de erro.
	 */
	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {

		int id = Integer.parseInt(req.getParameter("id"));
		AgendamentoDAO dao = new AgendamentoDAO();
		Agendamento agendamento = dao.buscaPorIdCompleto(id);

		String proximaPagina = "controladora?logica=ListaAgendamentoLogica";

		if (agendamento != null && agendamento.getStatus() == Status.AGENDADO) {
			LocalDateTime agora = LocalDateTime.now();
			LocalDateTime dataHoraAgendamento = agendamento.getDataAgendamento().atStartOfDay();
			long horasDeDiferenca = ChronoUnit.HOURS.between(agora, dataHoraAgendamento);

			if (horasDeDiferenca > 24) { 
				dao.atualizaStatus(id, Status.CANCELADO);
				res.sendRedirect(proximaPagina + "&msg=CanceladoSucesso"); 
				return;
			} else {
				req.setAttribute("erroCancelamento", "Não é possível cancelar agendamentos com menos de 24h de antecedência.");
				RequestDispatcher rd = req.getRequestDispatcher(proximaPagina); 
				rd.forward(req, res);
				return;
			}
		} else {
			req.setAttribute("erroCancelamento", "Agendamento não encontrado ou já processado.");
			RequestDispatcher rd = req.getRequestDispatcher(proximaPagina); 
			rd.forward(req, res);
			return; 
		}
	}
}