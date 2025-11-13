package br.jw.crc.logica;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CadastraServicoLogica implements Logica {
	/**
	 * Método apenas chama a pagina para cadastrar serviço
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	RequestDispatcher rd = req.getRequestDispatcher("/cadastraServico.jsp");
        rd.forward(req, res);
    }
}