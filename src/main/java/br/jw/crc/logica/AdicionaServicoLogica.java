package br.jw.crc.logica;

import br.jw.crc.dao.ServicoDAO;
import br.jw.crc.model.Servico;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdicionaServicoLogica implements Logica {
	/**
	 * Método recebe descrição e valor, instancia Servico com os valores, instancia ServicoDAO e o adiciona no banco.
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String descricao = req.getParameter("descricao");
        int valor = Integer.parseInt(req.getParameter("valor"));

        Servico servico = new Servico();
        servico.setDescricao(descricao);
        servico.setValor(valor); 

        ServicoDAO dao = new ServicoDAO();
        dao.adiciona(servico);

        res.sendRedirect("controladora?logica=ListaServicosLogica");
    }
}