package br.jw.crc.logica;

import br.jw.crc.dao.AdministratorDAO;
import br.jw.crc.model.Administrator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class EfetuaLoginLogica implements Logica {
	/**
	 * Método recebe login e senha, instancia AdministratorDAO, onde chama método que autentica as informações
	 *  do administrador, realiza login caso erro envia de volta par a pagina de login.
	 */
	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String login = req.getParameter("login");
		String senha = req.getParameter("senha");
		
		AdministratorDAO administratorDAO = new AdministratorDAO();
		Administrator administrator = administratorDAO.autenticaAdministrator(login, senha);
		if(administrator != null) {
			HttpSession session = req.getSession();
			session.setAttribute("logado", administrator);
			res.sendRedirect("menu.jsp");
		} else {
			res.sendRedirect("formulario-login.jsp");
		}
	}
}
