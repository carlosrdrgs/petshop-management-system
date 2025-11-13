package br.jw.crc.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import br.jw.crc.dao.AgendamentoDAO;
import br.jw.crc.dao.CaoDAO;
import br.jw.crc.model.Agendamento;
import br.jw.crc.model.Cao;
import br.jw.crc.model.Status;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdicionaAgendamentoLogica implements Logica {
	/**
	 * Método recebe cão, data, serviços, valida se pelo menos um serviço
	 * foi selecionado e se a data está disponível via AgendamentoDAO. Se válido,
	 * salva o agendamento completo e redireciona para a lista. Se inválido,
	 * retorna ao formulário com erro.
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {

        int caoId = Integer.parseInt(req.getParameter("caoId"));
        String dataAgendamentoParam = req.getParameter("dataAgendamento");
        String[] servicoIdsParam = req.getParameterValues("servicoIds"); 

        LocalDate dataAgendamento = LocalDate.parse(dataAgendamentoParam);
        
        List<Integer> servicoIds = new ArrayList<>();
        if (servicoIdsParam != null) {
            for (String idStr : servicoIdsParam) {
                servicoIds.add(Integer.parseInt(idStr));
            }
        } else {
             req.setAttribute("erroData", "Selecione ao menos um serviço.");
             RequestDispatcher rd = req.getRequestDispatcher("controladora?logica=CadastraAgendamentoLogica"); 
             rd.forward(req, res);
             return;
        }

        AgendamentoDAO agendamentoDao = new AgendamentoDAO();

        if (agendamentoDao.existeAgendamentoNaData(dataAgendamento)) {
            req.setAttribute("erroData", "Data indisponível - já existe agendamento neste dia.");
            RequestDispatcher rd = req.getRequestDispatcher("controladora?logica=CadastraAgendamentoLogica");
            rd.forward(req, res);
            return; 
        }

        CaoDAO caoDao = new CaoDAO();
        Cao cao = caoDao.buscaPorId(caoId); 

        Agendamento agendamento = new Agendamento();
        agendamento.setCao(cao);
        agendamento.setDataAgendamento(dataAgendamento);
        agendamento.setStatus(Status.AGENDADO);

        agendamentoDao.adicionaAgendamentoCompleto(agendamento, servicoIds);

        res.sendRedirect("controladora?logica=ListaAgendamentoLogica"); 
    }
}