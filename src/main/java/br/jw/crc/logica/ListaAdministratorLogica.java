package br.jw.crc.logica;

import java.util.List;
import br.jw.crc.dao.AdministratorDAO;
import br.jw.crc.model.Administrator;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListaAdministratorLogica implements Logica {
	/**
	 * Método instancia AdministratorDAO, cria um List de todos os administradores retorna a lista de administradores para serem apresentados na tela do usuário.
	 */
	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
		AdministratorDAO adminDAO = new AdministratorDAO();
		List<Administrator> administradores = adminDAO.getAdministrators();
		
		req.setAttribute("administradores", administradores);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("menuAdministradores.jsp");
		dispatcher.forward(req, res);
	}
}