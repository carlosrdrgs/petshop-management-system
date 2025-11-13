package br.jw.crc.logica;

import br.jw.crc.dao.AdministratorDAO;
import br.jw.crc.model.Administrator;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdicionaAdministratorLogica implements Logica {
	/**
	 * 
	 * Método recebe login, senha, confirmação, valida campos
	 * obrigatórios e se as senhas conferem. Se válido, adiciona via AdministratorDAO
	 * e redireciona para a lista de administradores. Se erro retorna res com mensagem.
	 *
	 */

    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        String login = req.getParameter("login");
        String senha = req.getParameter("senha");
        String confirmaSenha = req.getParameter("confirmaSenha");
        
        if (login == null || login.trim().isEmpty() || senha == null || senha.isEmpty()) {
            req.setAttribute("erroValidacao", "Login e senha são obrigatórios.");
            req.setAttribute("loginParam", login);
            
            RequestDispatcher rd = req.getRequestDispatcher("/cadastra-administrador.jsp");
            rd.forward(req, res);
            return;
        }

        if (!senha.equals(confirmaSenha)) {
            req.setAttribute("erroValidacao", "As senhas não conferem.");
            req.setAttribute("loginParam", login);
            
            RequestDispatcher rd = req.getRequestDispatcher("/cadastra-administrador.jsp");
            rd.forward(req, res);
            return;
        }
        
        Administrator admin = new Administrator();
        admin.setLogin(login);
        admin.setSenha(senha);
        
        AdministratorDAO adminDAO = new AdministratorDAO();
        adminDAO.adiciona(admin);
        
        res.sendRedirect("controladora?logica=ListaAdministratorLogica&msg=adicionado");
    }
}