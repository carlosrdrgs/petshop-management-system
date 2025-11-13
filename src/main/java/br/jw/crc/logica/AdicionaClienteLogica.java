package br.jw.crc.logica;

import java.time.LocalDate;
import br.jw.crc.dao.ClienteDAO;
import br.jw.crc.model.Cliente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdicionaClienteLogica implements Logica {
	// Formula para o CPF
    private static final String CPF_PATTERN = "(\\d{3}\\.?){3}-?\\d{2}";

	/**
	 * Método recebe parametros da classe Cliente, instancia Cliente, faz as validações exigidas, 
	 * adiciona no banco de dados, retorna res com sucesso, caso erro retorna mensagem.
	 */
    @Override
    public void executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        String nome = req.getParameter("nome");
        String cpf = req.getParameter("cpf");
        String email = req.getParameter("email");
        String telefone = req.getParameter("telefone");
        String dataNascimentoParam = req.getParameter("dataNascimento");
        
        req.setAttribute("nomeParam", nome);
        req.setAttribute("cpfParam", cpf);
        req.setAttribute("emailParam", email);
        req.setAttribute("telefoneParam", telefone);

        ClienteDAO clienteDAO = new ClienteDAO();
        
        if (nome == null || nome.trim().isEmpty() || 
            cpf == null || cpf.trim().isEmpty() ||
            email == null || email.trim().isEmpty() || 
            dataNascimentoParam == null || dataNascimentoParam.trim().isEmpty()) {
            
            req.setAttribute("erroValidacao", "Todos os campos marcados são obrigatórios.");
            RequestDispatcher rd = req.getRequestDispatcher("/cadastra-cliente.jsp");
            rd.forward(req, res);
            return;
        }

        if (!cpf.matches(CPF_PATTERN)) {
            req.setAttribute("erroValidacao", "O CPF informado não está em um formato válido.");
            RequestDispatcher rd = req.getRequestDispatcher("/cadastra-cliente.jsp");
            rd.forward(req, res);
            return;
        }
        
        if (clienteDAO.buscaIdPorCpf(cpf) != null) {
            req.setAttribute("erroValidacao", "O CPF " + cpf + " já está cadastrado.");
            RequestDispatcher rd = req.getRequestDispatcher("/cadastra-cliente.jsp");
            rd.forward(req, res);
            return;
        }

        LocalDate dataNascimento = null;
        try {
            dataNascimento = LocalDate.parse(dataNascimentoParam);
        } catch (Exception e) {
            req.setAttribute("erroValidacao", "A data de nascimento está em um formato inválido.");
            RequestDispatcher rd = req.getRequestDispatcher("/cadastra-cliente.jsp");
            rd.forward(req, res);
            return;
        }

        LocalDate hoje = LocalDate.now();
        if (dataNascimento.isAfter(hoje)) {
            req.setAttribute("erroValidacao", "A data de nascimento não pode ser uma data futura.");
            RequestDispatcher rd = req.getRequestDispatcher("/cadastra-cliente.jsp");
            rd.forward(req, res);
            return;
        }
        
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setEmail(email);
        cliente.setTelefone(telefone);
        cliente.setDataNascimento(dataNascimento);
        
        
        clienteDAO.adiciona(cliente);
        
        res.sendRedirect("controladora?logica=ListaClienteLogica&msg=adicionado");
    }
}