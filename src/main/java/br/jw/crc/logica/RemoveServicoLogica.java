package br.jw.crc.logica;

import br.jw.crc.dao.ServicoDAO;
import br.jw.crc.model.Servico; // Importa Servico
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RemoveServicoLogica implements Logica {

	/**
	 * Método recebe parametro de ID, e faz solicitação para DAO deletar do banco de dados
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));

        Servico servico = new Servico();
        servico.setId(id);

        ServicoDAO dao = new ServicoDAO();
        dao.remove(servico); 
        res.sendRedirect("controladora?logica=ListaServicosLogica");
    }
}