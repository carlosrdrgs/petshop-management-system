package br.jw.crc.logica;

import br.jw.crc.dao.CaoDAO;
import br.jw.crc.model.Cao;
import br.jw.crc.model.Porte;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProcessaAlteraCaoLogica implements Logica {
	/**
	 * Método recebe parametros da classe cao, instancia cao, faz os ajustes necessários, retorna res com cliente alterado, caso erro retorna mensagem.
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        String nome = req.getParameter("nome");
        String raca = req.getParameter("raca");
        String porteStr = req.getParameter("porte");

        Cao cao = new Cao();
        cao.setId(id);
        cao.setNome(nome);
        cao.setRaca(raca);

        try {
            cao.setPorte(Porte.valueOf(porteStr.toUpperCase()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        CaoDAO dao = new CaoDAO();
        dao.altera(cao);

        res.sendRedirect("controladora?logica=ListaCaoLogica");
    }
}