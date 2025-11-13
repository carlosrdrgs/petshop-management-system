package br.jw.crc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import br.jw.crc.jdbc.ConnectionFactory;
import br.jw.crc.model.Cao;
import br.jw.crc.model.Porte;

public class CaoDAO {
private Connection connection;
	
	public CaoDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void adiciona(Cao cao, Long id) {
		String sql = "insert into cao "
				+ "(nome,raca,porte,cliente_id)"
				+ "values (?,?,?,?)";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, cao.getNome());
			preparedStatement.setString(2, cao.getRaca());
			preparedStatement.setString(3, cao.getPorte().toString());
			preparedStatement.setLong(4, id);
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void altera(Cao cao) {
		String sql = "update cao "
				+ "set nome=?, raca=?, porte=?"
				+ " where id=?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, cao.getNome());
			preparedStatement.setString(2, cao.getRaca());
			preparedStatement.setString(3, cao.getPorte().toString());
			preparedStatement.setLong(4, cao.getId());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void remove(Cao cao) {
		String sql = "delete from cao where id=?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, cao.getId());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void remove(int id) {
String sql = "delete from cao where id=?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Cao> getCaes(){
		List<Cao> caes = new ArrayList<Cao>();
		try {
			PreparedStatement statement = this.connection.prepareStatement (
					 "select * from cao");
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				Cao cao = new Cao();
				cao.setId(rs.getInt("id"));
				cao.setNome(rs.getString("nome"));
				cao.setRaca(rs.getString("raca"));
				cao.setPorte(Porte.valueOf(rs.getString("porte").toUpperCase()));
				
				caes.add(cao);
			}
			
			return caes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	public List<Cao> buscaPorClienteId(Long id) {
		List<Cao> caesCliente = new ArrayList<Cao>();
	    String sql = "select * from cao where cliente_id = ?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Cao cao = new Cao();
				cao.setId(resultSet.getInt("id"));
				cao.setNome(resultSet.getString("nome"));
				cao.setRaca(resultSet.getString("raca"));
				cao.setPorte(Porte.valueOf(resultSet.getString("porte").toUpperCase()));
			
				caesCliente.add(cao);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return caesCliente;
	}
	
	public List<Cao> getCaesPorClienteId(Long id) {
		List<Cao> caesCliente = new ArrayList<Cao>();
		String sql = "select * from cao where cliente_id = ?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Cao cao = new Cao();
				cao.setId(resultSet.getInt("id"));
				cao.setNome(resultSet.getString("nome"));
				cao.setRaca(resultSet.getString("raca"));
				cao.setPorte(Porte.valueOf(resultSet.getString("porte").toUpperCase()));
			
				caesCliente.add(cao);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return caesCliente;
	}

	/**
	 * Método recebe um ID de cão, executa uma consulta na tabela 'cao'
	 * para encontrar a chave estrangeira 'cliente_id'. Retorna o ID (Long) 
	 * do cliente (dono) ou null se não for encontrado.
	 */
	public Long getClienteIdPorCaoId(int caoId) {
	    String sql = "SELECT cliente_id FROM cao WHERE id = ?";
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Long clienteId = null;

	    try {
	        stmt = this.connection.prepareStatement(sql);
	        stmt.setInt(1, caoId);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            clienteId = rs.getLong("cliente_id");
	            if (rs.wasNull()) { 
	                clienteId = null;
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }
	    return clienteId;
	}
	
	/**
	 * Método recebe um ID, executa uma busca no banco pela chave primária.
	 * Mapeia o resultado para um objeto e o retorna.
	 * Retorna null se o ID não for encontrado.
	 */
	public Cao buscaPorId(long id) {
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Cao caoEncontrado = null;

	    String sql = "select * from cao where id = ?";

	    try {
	        stmt = this.connection.prepareStatement(sql);
	        stmt.setLong(1, id);

	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            caoEncontrado = new Cao();
	            caoEncontrado.setId(rs.getInt("id"));
	            caoEncontrado.setNome(rs.getString("nome"));
	            caoEncontrado.setRaca(rs.getString("raca"));

	            String porteStr = rs.getString("porte");
	            if (porteStr != null) {
	                try {
	                    caoEncontrado.setPorte(Porte.valueOf(porteStr.toUpperCase()));
	                } catch (Exception e) {
	                    throw new RuntimeException(e); 
	                }
	            }
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }

	    return caoEncontrado;
	}
	
}
