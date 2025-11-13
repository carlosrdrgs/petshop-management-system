package br.jw.crc.logica;

import java.time.LocalDate;
import br.jw.crc.dao.ClienteDAO;
import br.jw.crc.model.Cliente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProcessaAlteraClienteLogica implements Logica {

	// Formula para o CPF
	private static final String CPF_PATTERN = "(\\d{3}\\.?){3}-?\\d{2}";
	
	/**
	 * Método recebe parametros da classe Cliente, instancia Cliente, faz asvalidações exigidas, retorna res com cliente alterado, caso erro retorna mensagem.
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        long id = Long.parseLong(req.getParameter("id"));
        String nome = req.getParameter("nome");
        String cpf = req.getParameter("cpf");
        String email = req.getParameter("email");
        String telefone = req.getParameter("telefone");
        String dataNascimentoParam = req.getParameter("dataNascimento");
        
        LocalDate dataNascimento = null;
        
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setEmail(email);
        cliente.setTelefone(telefone);

        try {
            dataNascimento = LocalDate.parse(dataNascimentoParam);
            cliente.setDataNascimento(dataNascimento);
        } catch (Exception e) {
            req.setAttribute("erroValidacao", "Data de nascimento inválida.");
            req.setAttribute("cliente", cliente);
            RequestDispatcher rd = req.getRequestDispatcher("/altera-cliente.jsp");
            rd.forward(req, res);
            return;
        }

        LocalDate hoje = LocalDate.now();
        if (dataNascimento.isAfter(hoje)) {
            req.setAttribute("erroValidacao", "A data de nascimento não pode ser uma data futura.");
            req.setAttribute("cliente", cliente);
            RequestDispatcher rd = req.getRequestDispatcher("/altera-cliente.jsp");
            rd.forward(req, res);
            return;
        }
        
        if (!cpf.matches(CPF_PATTERN)) {
            req.setAttribute("erroValidacao", "O CPF informado não está em um formato válido.");
            req.setAttribute("cliente", cliente);
            RequestDispatcher rd = req.getRequestDispatcher("/altera-cliente.jsp");
            rd.forward(req, res);
            return;
        }

        ClienteDAO dao = new ClienteDAO();
        dao.altera(cliente);

        res.sendRedirect("controladora?logica=ListaClienteLogica&msg=alterado");
    }
}