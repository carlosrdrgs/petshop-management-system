package br.jw.crc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.jw.crc.jdbc.ConnectionFactory;
import br.jw.crc.model.Cliente;

public class ClienteDAO {
	private Connection connection;
	
	public ClienteDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void adiciona(Cliente cliente) {
		String sql = "insert into cliente "
				+ "(nome,cpf,data_nascimento,email,telefone)"
				+ "values (?,?,?,?,?)";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, cliente.getNome());
			preparedStatement.setString(2, cliente.getCpf());
			preparedStatement.setDate(3, Date.valueOf(cliente.getDataNascimento()));
			preparedStatement.setString(4, cliente.getEmail());
			preparedStatement.setString(5, cliente.getTelefone());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void altera(Cliente cliente) {
		String sql = "update cliente "
				+ "set nome=?, cpf=?, data_nascimento=?, email=?, telefone=?"
				+ " where id=?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, cliente.getNome());
			preparedStatement.setString(2, cliente.getCpf());
			preparedStatement.setDate(3, Date.valueOf(cliente.getDataNascimento()));
			preparedStatement.setString(4, cliente.getEmail());
			preparedStatement.setString(5, cliente.getTelefone());
			preparedStatement.setLong(6, cliente.getId());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void remove(Cliente cliente) {
		String sql = "delete from cliente where id=?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, cliente.getId());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void remove(int id) {
		String sql = "delete from cliente where id=?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Cliente> getClientes(){
		List<Cliente> clientes = new ArrayList<Cliente>();
		try {
			PreparedStatement statement = this.connection.prepareStatement (
					 "select * from cliente");
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(rs.getLong("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setCpf(rs.getString("cpf"));
				Date dataDoBanco = rs.getDate("data_nascimento");
				if (dataDoBanco != null) {
				    cliente.setDataNascimento(dataDoBanco.toLocalDate());
				}
				cliente.setEmail(rs.getString("email"));
				cliente.setTelefone(rs.getString("telefone"));
				
				clientes.add(cliente);
			}
			
			return clientes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	/**
	 * Método recebe um CPF, executa uma consulta no banco para encontrar
	 * o ID do cliente correspondente. Retorna o ID (Long) se encontrado,
	 * ou null se não existir.
	 */
	public Long buscaIdPorCpf(String cpf) {
	    String sql = "select id from cliente where cpf = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, cpf);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) { 
	            return rs.getLong("id");
	        } else {
	            return null;
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}

	/**
	 * Método recebe um ID, executa uma busca no banco pela chave primária.
	 * Mapeia o resultado para um objeto e o retorna.
	 * Retorna null se o ID não for encontrado.
	 */
	public Cliente buscaPorId(long id) {
		PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Cliente cliente = null;

	    String sql = "select * from cliente where id = ?";

	    try {
	        preparedStatement = this.connection.prepareStatement(sql);
	        preparedStatement.setLong(1, id);

	        resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            cliente = new Cliente();
	            cliente.setId(resultSet.getLong("id"));
	            cliente.setNome(resultSet.getString("nome"));
	            cliente.setCpf(resultSet.getString("cpf"));

	            Date dataDoBanco = resultSet.getDate("data_nascimento");
	            if (dataDoBanco != null) {
	                cliente.setDataNascimento(dataDoBanco.toLocalDate());
	            }
	            cliente.setEmail(resultSet.getString("email"));
	            cliente.setTelefone(resultSet.getString("telefone"));
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	    return cliente;
	}
}
