package br.jw.crc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.jw.crc.jdbc.ConnectionFactory;
import br.jw.crc.logica.PasswordUtil;
import br.jw.crc.model.Administrator;

public class AdministratorDAO {
    private Connection connection;

    public AdministratorDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(Administrator administrator) {
        String sql = "insert into administrator (login, senha) values (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        	// Transforma a senha em uma hash (criptografia)
            String hashedPassword = PasswordUtil.hashPassword(administrator.getSenha());
            
            preparedStatement.setString(1, administrator.getLogin());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.execute();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar administrador: " + e.getMessage(), e);
        }
    }
    
    public void altera(Administrator administrator) {
		String sql = "update administrator "
				+ "set login=?, senha=?"
				+ " where id=?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            String hashedPassword = PasswordUtil.hashPassword(administrator.getSenha());
            
			preparedStatement.setString(1, administrator.getLogin());
			preparedStatement.setString(2, hashedPassword);
			preparedStatement.setLong(3, administrator.getId());
			
			preparedStatement.execute();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao alterar administrador: " + e.getMessage(), e);
		}
	}
    
    public void remove(Administrator administrator) {
		String sql = "delete from administrator where id=?";
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setLong(1, administrator.getId());
			
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao remover administrador: " + e.getMessage(), e);
		}
	}

    public Administrator autenticaAdministrator(String login, String senha) {
    	String sql = "select * from administrator where login=?";
    	
    	try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, login);
			
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				
				if (resultSet.next()) {
                    String hashedPasswordFromDB = resultSet.getString("senha");
                    
                    if (PasswordUtil.checkPassword(senha, hashedPasswordFromDB)) {
                        
                        Administrator administrator = new Administrator();
                        administrator.setId(resultSet.getInt("id"));
                        administrator.setLogin(resultSet.getString("login"));
                        
                        return administrator;
                    }
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao autenticar administrador: " + e.getMessage(), e);
		}
    	
    	return null;
    }
    
    public List<Administrator> getAdministrators() {
		List<Administrator> administradores = new ArrayList<>();
		String sql = "select * from administrator";

		try (PreparedStatement statement = this.connection.prepareStatement(sql);
			 ResultSet rs = statement.executeQuery()) {
			
			while (rs.next()) {
				Administrator administrator = new Administrator();
				administrator.setId(rs.getInt("id"));
				administrator.setLogin(rs.getString("login"));
				administrator.setSenha(rs.getString("senha")); 
				
				administradores.add(administrator);
			}
			
			return administradores;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
}