package br.jw.crc.logica;

import java.util.List;
import br.jw.crc.dao.CaoDAO;
import br.jw.crc.dao.ClienteDAO;
import br.jw.crc.dao.ServicoDAO;
import br.jw.crc.model.Cliente;
import br.jw.crc.model.Servico;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CadastraAgendamentoLogica implements Logica {
	/**
	 * Método instancia ClienteDAO, CaoDAO e ServicoDAO para buscar a lista de 
	 * clientes (com seus cães) e a lista de serviços.
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
        ClienteDAO clienteDao = new ClienteDAO();
        List<Cliente> clientes = clienteDao.getClientes();
        
        CaoDAO caoDao = new CaoDAO();
        for (Cliente cliente : clientes) {
             cliente.setCaes(caoDao.buscaPorClienteId(cliente.getId()));
        }

        ServicoDAO servicoDao = new ServicoDAO();
        List<Servico> servicos = servicoDao.getServicos();

        req.setAttribute("clientes", clientes);
        req.setAttribute("servicos", servicos);

        RequestDispatcher rd = req.getRequestDispatcher("/menuAgendamento.jsp");
        rd.forward(req, res);
    }
}