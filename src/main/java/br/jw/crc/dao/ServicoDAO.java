package br.jw.crc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.jw.crc.jdbc.ConnectionFactory;
import br.jw.crc.model.Servico;

public class ServicoDAO {
	private Connection connection;
	
	public ServicoDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void adiciona(Servico servico) {
		String sql = "insert into servico (descricao, valor) values (?, ?)";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, servico.getDescricao());
			preparedStatement.setLong(2, servico.getValor());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	public void altera(Servico servico) {
		String sql = "update servico set descricao = ?, valor = ? WHERE id = ?;";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, servico.getDescricao());
			preparedStatement.setLong(2, servico.getValor());
			preparedStatement.setLong(3, servico.getId());
			
			preparedStatement.execute();
		    preparedStatement.close();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void remove(Servico servico) {
		String sql = "delete from servico where id = ?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, servico.getId());
			
			preparedStatement.execute();
		    preparedStatement.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Servico> getServicos(){
		List<Servico> servicos = new ArrayList<Servico>();
		try {
			PreparedStatement statement = this.connection.prepareStatement (
					 "select * from servico");
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				Servico servico = new Servico();
				servico.setId(rs.getInt("id"));
				servico.setDescricao(rs.getString("descricao"));
				servico.setValor(rs.getInt("valor"));
				
				servicos.add(servico);
			}
			
			return servicos;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	/**
	 * Método recebe um ID, executa uma busca no banco pela chave primária.
	 * Mapeia o resultado para um objeto e o retorna.
	 * Retorna null se o ID não for encontrado.
	 */
	public Servico buscaPorId(int id) {
        String sql = "select * from servico where id = ?";
        Servico servico = null;
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    servico = new Servico();
                    servico.setId(rs.getInt("id"));
                    servico.setDescricao(rs.getString("descricao"));
                    servico.setValor(rs.getInt("valor")); 
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return servico;
    }
	
	/**
	 * Método recebe um ID de agendamento, busca no banco todos os serviços
	 * associados a esse agendamento através da tabela de junção 
	 * (agendamento_servico) e retorna uma Lista de Servicos.
	 */
	public List<Servico> buscaServicosPorAgendamentoId(int agendamentoId) {
	    List<Servico> servicosDoAgendamento = new ArrayList<>();
	    String sql = "SELECT s.id, s.descricao, s.valor " +
	                 "FROM servico s " +
	                 "JOIN agendamento_servico ags ON s.id = ags.servico_id " +
	                 "WHERE ags.agendamento_id = ?";

	    PreparedStatement stmt = null;
	    ResultSet rs = null;          

	    try {
	        stmt = this.connection.prepareStatement(sql);
	        stmt.setInt(1, agendamentoId); 

	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            Servico servico = new Servico();
	            servico.setId(rs.getInt("id"));
	            servico.setDescricao(rs.getString("descricao"));
	            servico.setValor((int) rs.getDouble("valor"));
	            
	            servicosDoAgendamento.add(servico);
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	    
	    return servicosDoAgendamento; 
	}
}
