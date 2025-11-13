package br.jw.crc.logica;

import br.jw.crc.dao.ServicoDAO;
import br.jw.crc.model.Servico;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProcessaAlteraServicoLogica implements Logica {

	/**
	 * Método recebe parametro de ID e valor, chama DAO para buscar (ID) e altera valor (novoValor) logo após atualiza o serviço.
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        int novoValor = Integer.parseInt(req.getParameter("valor"));

        ServicoDAO dao = new ServicoDAO();
        Servico servico = dao.buscaPorId(id);

        if (servico != null) {
            servico.setValor(novoValor);

            dao.altera(servico);
        }

        res.sendRedirect("controladora?logica=ListaServicosLogica");
    }
}