package br.jw.crc.logica;

import java.time.LocalDate;
import java.util.List;
import br.jw.crc.dao.AgendamentoDAO;
import br.jw.crc.model.Agendamento;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date; // <<< --- 1. IMPORTE java.sql.Date

public class GerarRelatorioFaturamentoLogica implements Logica {
	/**
	 * Método recebe data de inicio e de fim, instancia AgendamentoDAO e lista todos os agendamentos realizados neste periodo, soma os valores em uma variavel, 
	 * envia os dados para serem exibidos pelo front-end
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {

        String dataInicioParam = req.getParameter("dataInicio");
        String dataFimParam = req.getParameter("dataFim");

        LocalDate dataInicioLocal = LocalDate.parse(dataInicioParam);
        LocalDate dataFimLocal = LocalDate.parse(dataFimParam);

        AgendamentoDAO dao = new AgendamentoDAO();
        List<Agendamento> resultados = dao.listarRealizadosPorPeriodo(dataInicioLocal, dataFimLocal);

        double faturamentoTotal = 0;
        for (Agendamento ag : resultados) {
            faturamentoTotal += ag.getValorTotal(); 
        }

        req.setAttribute("resultadosRelatorio", resultados);
        req.setAttribute("faturamentoTotal", faturamentoTotal);

        req.setAttribute("dataInicio", Date.valueOf(dataInicioLocal)); 
        req.setAttribute("dataFim", Date.valueOf(dataFimLocal));     

        RequestDispatcher rd = req.getRequestDispatcher("/relatorio-faturamento.jsp");
        rd.forward(req, res);
    }
}