package br.jw.crc.logica;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

public class EfetuaLogoutLogica implements Logica {
	/**
	 * Método apenas invalida a sessão, assim efetuando "logout" do usuário
	 */
	@Override
	public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		session.invalidate();
		res.sendRedirect("formulario-login.jsp");
	}
}
