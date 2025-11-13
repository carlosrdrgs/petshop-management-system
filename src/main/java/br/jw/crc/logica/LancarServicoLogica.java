package br.jw.crc.logica;

import br.jw.crc.dao.AgendamentoDAO;
import br.jw.crc.dao.CaoDAO;     
import br.jw.crc.dao.ClienteDAO;  
import br.jw.crc.model.Agendamento;
import br.jw.crc.model.Cliente;  
import br.jw.crc.model.Servico;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LancarServicoLogica implements Logica {

	/**
	 * Método recebe um ID de Agendamento, faz uma busca completa por meio do AgendamentoDAO, calcula o valor total, verifica a regra de desconto e encaminha os dados
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {

        int id = Integer.parseInt(req.getParameter("id"));

        AgendamentoDAO agendamentoDao = new AgendamentoDAO();
        Agendamento agendamento = agendamentoDao.buscaPorIdCompleto(id); 

        Cliente clienteDoAgendamento = null; 

        if (agendamento == null || agendamento.getCao() == null) {
            res.sendRedirect("controladora?logica=ListaAgendamentosLogica&erro=AgendamentoNaoEncontrado");
            return;
        } else {

             CaoDAO caoDao = new CaoDAO();

             Long clienteIdDoCao = caoDao.getClienteIdPorCaoId(agendamento.getCao().getId()); 
             
             if (clienteIdDoCao != null) {
                 ClienteDAO clienteDao = new ClienteDAO();
                 clienteDoAgendamento = clienteDao.buscaPorId(clienteIdDoCao); 
             } else {
                 System.err.println("Cão com ID " + agendamento.getCao().getId() + " não tem cliente associado.");
             }
        }

        double valorTotalCalculado = 0;
        boolean podeAplicarDesconto = false;
        if (agendamento.getServicos() != null) {
            for (Servico s : agendamento.getServicos()) {
                if(s != null){
                     valorTotalCalculado += s.getValor();
                }
            }
            if (agendamento.getServicos().size() >= 3) {
                podeAplicarDesconto = true;
            }
        }

        req.setAttribute("agendamento", agendamento);
        req.setAttribute("clienteDoAgendamento", clienteDoAgendamento); 
        req.setAttribute("valorTotalCalculado", valorTotalCalculado);
        req.setAttribute("podeAplicarDesconto", podeAplicarDesconto);

        RequestDispatcher rd = req.getRequestDispatcher("/lancar-servico.jsp");
        rd.forward(req, res);
    }
}