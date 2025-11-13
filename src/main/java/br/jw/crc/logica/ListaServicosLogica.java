// Em ListaServicosLogica.java
package br.jw.crc.logica;

import java.util.List;
import br.jw.crc.dao.ServicoDAO;
import br.jw.crc.model.Servico;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListaServicosLogica implements Logica {
	/**
	 * Método instancia ServicoDAO, cria um List de todos os serviços e retorna a lista de serviços para serem apresentados na tela do usuário.
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
        ServicoDAO servicoDAO = new ServicoDAO();
        List<Servico> servicos = servicoDAO.getServicos(); 

        req.setAttribute("servicos", servicos); 

        RequestDispatcher dispatcher = req.getRequestDispatcher("/menuServicos.jsp");
        dispatcher.forward(req, res);
    }
}