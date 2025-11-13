package br.jw.crc.controlador;

import java.io.IOException;

import br.jw.crc.logica.Logica;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/controladora")
public class ControllerServlet extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			 throws ServletException, IOException {
		
		String acao = req.getParameter("logica");
		String nomeClasse = "br.jw.crc.logica." + acao;
		
		
		
		try {
			Class classe = Class.forName(nomeClasse);
			Logica logica = (Logica) classe.getDeclaredConstructor().newInstance();
			logica.executa(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}
}
