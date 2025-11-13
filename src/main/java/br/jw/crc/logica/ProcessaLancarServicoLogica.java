// Em ProcessaLancarServicoLogica.java
package br.jw.crc.logica;

import java.time.LocalDate;
import br.jw.crc.dao.AgendamentoDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class ProcessaLancarServicoLogica implements Logica {
	/**
	 * Método recebe parametro de ID, seta data da execução, caso conveniente aplica desconto e calcula valor final, chama DAO para atualizar status.
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {

        int id = Integer.parseInt(req.getParameter("id"));
        String dataExecucaoParam = req.getParameter("dataExecucao");
        LocalDate dataExecucao = LocalDate.parse(dataExecucaoParam);
        String aplicarDescontoParam = req.getParameter("aplicarDesconto");
        boolean aplicarDesconto = (aplicarDescontoParam != null && aplicarDescontoParam.equals("true"));
        double valorOriginal = Double.parseDouble(req.getParameter("valorOriginal"));

        double valorFinal = valorOriginal;
        boolean descontoFoiAplicado = false;
        if (aplicarDesconto) {
            valorFinal = valorOriginal * 0.9;
            descontoFoiAplicado = true;
        }

        AgendamentoDAO dao = new AgendamentoDAO();
        dao.atualizaParaRealizado(id, dataExecucao, valorFinal, descontoFoiAplicado);

        res.sendRedirect("controladora?logica=ListaAgendamentoLogica");
    }
}