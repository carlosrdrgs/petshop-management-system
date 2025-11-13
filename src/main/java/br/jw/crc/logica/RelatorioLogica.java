package br.jw.crc.logica;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RelatorioLogica implements Logica {
	/**
	 * Apenas chama a pagina de relatorio
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
        RequestDispatcher rd = req.getRequestDispatcher("/relatorio-faturamento.jsp"); 
        rd.forward(req, res);
    }
}