package br.jw.crc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.jw.crc.jdbc.ConnectionFactory;
import br.jw.crc.model.Agendamento;
import br.jw.crc.model.Cao;
import br.jw.crc.model.Cliente;
import br.jw.crc.model.Servico;
import br.jw.crc.model.Status;

public class AgendamentoDAO {

    private Connection connection;

    public AgendamentoDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(Agendamento agendamento) {
    	String sql = "insert into agendamento (data_agendamento, status, cao_id) values (?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setDate(1, Date.valueOf(agendamento.getDataAgendamento()));
            stmt.setString(2, agendamento.getStatus().toString());
            stmt.setInt(3, agendamento.getCao().getId());

            stmt.execute();
            stmt.close(); 

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void alteraStatus(int id, Status novoStatus, LocalDate dataExecucao) {
    	String sql = "update agendamento set status = ?, data_execucao = ? WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, novoStatus.toString());
            stmt.setDate(2, Date.valueOf(dataExecucao));
            stmt.setInt(3, id);

            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(int id) {
        String sql = "delete from agendamento where id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
	 * Método verifica no banco se já existe um agendamento com status 'AGENDADO' 
	 * para uma data específica. Retorna true se a data estiver ocupada.
	 */
    public boolean existeAgendamentoNaData(LocalDate data) {
        String sql = "select count(*) from agendamento where data_agendamento = ? and status = 'AGENDADO'";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(data));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    
    /**
	 * Método busca agendamentos com status 'AGENDADO' a partir de uma data, 
	 * populando os objetos Cão e a Lista de Serviços de cada agendamento.
	 */
    public List<Agendamento> listarPendentes(LocalDate dataInicio) {
        String sql = "SELECT id, cao_id, data_agendamento, status FROM agendamento WHERE data_agendamento >= ? AND status = 'AGENDADO' ORDER BY data_agendamento ASC";
        List<Agendamento> agendamentos = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        CaoDAO caoDao = new CaoDAO();           
        ServicoDAO servicoDao = new ServicoDAO();

        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(dataInicio));
            rs = stmt.executeQuery();

            while (rs.next()) {
                Agendamento agendamento = new Agendamento();
                agendamento.setId(rs.getInt("id"));

                Date dataDb = rs.getDate("data_agendamento");
                if (dataDb != null) {
                    agendamento.setDataAgendamento(dataDb.toLocalDate());
                }

                String statusStr = rs.getString("status");
                 if (statusStr != null){
                    try {
                        agendamento.setStatus(Status.valueOf(statusStr.toUpperCase()));
                     } catch (IllegalArgumentException e) {
                         System.err.println("Valor inválido para Status no banco (ID Ag: " + agendamento.getId() + "): " + statusStr);
                         continue;
                     }
                }

                int caoId = rs.getInt("cao_id");

                Cao cao = caoDao.buscaPorId(caoId);
                agendamento.setCao(cao);

                List<Servico> servicos = servicoDao.buscaServicosPorAgendamentoId(agendamento.getId());
                agendamento.setServicos(servicos);

                agendamentos.add(agendamento);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar agendamentos pendentes.", e);
        } 
        return agendamentos;
    }
    
    /**
	 * Método atualiza o status (ex: AGENDADO, CANCELADO) de um agendamento 
	 * específico no banco de dados, buscando pelo ID.
	 */
    public void atualizaStatus(int id, Status novoStatus) {
        String sql = "UPDATE agendamento SET status = ? WHERE id = ?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, novoStatus.toString());
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status do agendamento.", e);
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                 throw new RuntimeException("Erro ao fechar PreparedStatement.", e);
            }
        }
    }
    
    /**
	 * Método busca o histórico de agendamentos com status 'REALIZADO' de um 
	 * cão específico, populando seus dados e serviços associados.
	 */
    public List<Agendamento> listarHistoricoPorCaoId(int caoId) {
        String sql = "SELECT id, cao_id, data_agendamento, data_execucao, status, valor_total, desconto_aplicado " +
                     "FROM agendamento " +
                     "WHERE cao_id = ? AND status = ? " +
                     "ORDER BY data_execucao DESC";

        List<Agendamento> historico = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ServicoDAO servicoDao = new ServicoDAO();
        CaoDAO caoDao = new CaoDAO(); 

        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, caoId);
            stmt.setString(2, Status.REALIZADO.toString());
            rs = stmt.executeQuery();

            while (rs.next()) {
                Agendamento agendamento = new Agendamento();
                agendamento.setId(rs.getInt("id"));

                Date dataAg = rs.getDate("data_agendamento");
                if (dataAg != null) agendamento.setDataAgendamento(dataAg.toLocalDate());

                Date dataEx = rs.getDate("data_execucao");
                if (dataEx != null) agendamento.setDataExecucao(dataEx.toLocalDate()); 

                agendamento.setValorTotal(rs.getDouble("valor_total"));
                agendamento.setDescontoAplicado(rs.getBoolean("desconto_aplicado")); 

                agendamento.setStatus(Status.REALIZADO);

                Cao cao = caoDao.buscaPorId(rs.getInt("cao_id"));
                agendamento.setCao(cao); 

                List<Servico> servicos = servicoDao.buscaServicosPorAgendamentoId(agendamento.getId());
                agendamento.setServicos(servicos);

                historico.add(agendamento);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar histórico de agendamentos por cão.", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar recursos JDBC.", e);
            }
        }
        return historico;
    }

	/**
	 * Método busca um agendamento único por ID, populando seus dados 
	 * completos (Cão e Lista de Serviços associados).
	 */
    public Agendamento buscaPorIdCompleto(int id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Agendamento agendamento = null;

        String sql = "SELECT id, cao_id, data_agendamento, status FROM agendamento WHERE id = ?";

        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                agendamento = new Agendamento();
                agendamento.setId(rs.getInt("id"));

                Date dataAg = rs.getDate("data_agendamento");
                if (dataAg != null) {
                    agendamento.setDataAgendamento(dataAg.toLocalDate());
                }

                String statusStr = rs.getString("status");
                if (statusStr != null) {
                     try {
                         agendamento.setStatus(Status.valueOf(statusStr.toUpperCase()));
                     } catch (IllegalArgumentException e) {
                         throw new RuntimeException("Dado inválido para Status no banco.", e);
                     }
                }

                int caoId = rs.getInt("cao_id");

                CaoDAO caoDao = new CaoDAO();
                Cao cao = caoDao.buscaPorId(caoId);
                agendamento.setCao(cao);

                ServicoDAO servicoDao = new ServicoDAO();
                List<Servico> servicos = servicoDao.buscaServicosPorAgendamentoId(agendamento.getId());
                agendamento.setServicos(servicos);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return agendamento;
    }
    
    /**
	 * Método atualiza um agendamento para 'REALIZADO', salvando a data 
	 * de execução, o valor final cobrado e se houve desconto aplicado.
	 */
    public void atualizaParaRealizado(int id, LocalDate dataExecucao, double valorFinal, boolean descontoAplicado) {
        String sql = "UPDATE agendamento SET status = ?, data_execucao = ?, valor_total = ?, desconto_aplicado = ? WHERE id = ?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, Status.REALIZADO.toString());
            stmt.setDate(2, Date.valueOf(dataExecucao));
            stmt.setDouble(3, valorFinal);
            stmt.setBoolean(4, descontoAplicado);
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar agendamento para realizado.", e);
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar PreparedStatement.", e);
            }
        }
    }
    
    /**
	 * Método salva um novo agendamento e seus serviços associados usando uma 
	 * transação e batch insert na tabela de junção 'agendamento_servico'.
	 */
    public void adicionaAgendamentoCompleto(Agendamento agendamento, List<Integer> servicoIds) {
        String sqlAgendamento = "insert into agendamento (cao_id, data_agendamento, status) values (?, ?, ?)";
        String sqlAgendamentoServico = "insert into agendamento_servico (agendamento_id, servico_id) values (?, ?)";

        PreparedStatement stmtAgendamento = null;
        PreparedStatement stmtAgendamentoServico = null;
        ResultSet generatedKeys = null;
        boolean originalAutoCommitState = true; 
        try {
            originalAutoCommitState = connection.getAutoCommit();
            connection.setAutoCommit(false);

            stmtAgendamento = connection.prepareStatement(sqlAgendamento, Statement.RETURN_GENERATED_KEYS);
            stmtAgendamento.setInt(1, agendamento.getCao().getId());
            stmtAgendamento.setDate(2, Date.valueOf(agendamento.getDataAgendamento()));
            stmtAgendamento.setString(3, agendamento.getStatus().toString());
            stmtAgendamento.executeUpdate();

            generatedKeys = stmtAgendamento.getGeneratedKeys();
            if (generatedKeys.next()) {
                int agendamentoId = generatedKeys.getInt(1);
                agendamento.setId(agendamentoId);

                stmtAgendamentoServico = connection.prepareStatement(sqlAgendamentoServico);
                for (Integer servicoId : servicoIds) {
                    stmtAgendamentoServico.setInt(1, agendamentoId);
                    stmtAgendamentoServico.setInt(2, servicoId);
                    stmtAgendamentoServico.addBatch(); 
                }
                stmtAgendamentoServico.executeBatch(); 

                connection.commit();

            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar agendamento completo.", e);
        }
    }
    
    /**
	 * Método busca todos os agendamentos 'REALIZADO' entre duas datas, 
	 * populando Cão e Serviços, para o relatório de faturamento.
	 */
    public List<Agendamento> listarRealizadosPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        String sql = "SELECT id, cao_id, data_agendamento, data_execucao, status, valor_total, desconto_aplicado " +
                     "FROM agendamento " +
                     "WHERE status = ? AND data_execucao BETWEEN ? AND ? " + 
                     "ORDER BY data_execucao ASC"; 

        List<Agendamento> agendamentosRealizados = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        CaoDAO caoDao = new CaoDAO();
        ServicoDAO servicoDao = new ServicoDAO(); 

        try {
            stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, Status.REALIZADO.toString());
            stmt.setDate(2, Date.valueOf(dataInicio)); 
            stmt.setDate(3, Date.valueOf(dataFim));   
            rs = stmt.executeQuery();

            while (rs.next()) {
                Agendamento agendamento = new Agendamento();
                agendamento.setId(rs.getInt("id"));

                Date dataAg = rs.getDate("data_agendamento");
                if (dataAg != null) agendamento.setDataAgendamento(dataAg.toLocalDate());

                Date dataEx = rs.getDate("data_execucao");
                if (dataEx != null) agendamento.setDataExecucao(dataEx.toLocalDate());

                agendamento.setValorTotal(rs.getDouble("valor_total"));
                agendamento.setDescontoAplicado(rs.getBoolean("desconto_aplicado"));
                agendamento.setStatus(Status.REALIZADO);

                int caoId = rs.getInt("cao_id");
                Cao cao = caoDao.buscaPorId(caoId);
                agendamento.setCao(cao);

                List<Servico> servicos = servicoDao.buscaServicosPorAgendamentoId(agendamento.getId());
                agendamento.setServicos(servicos);

                agendamentosRealizados.add(agendamento);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar agendamentos realizados por período.", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar recursos JDBC.", e);
            }
        }
        return agendamentosRealizados;
    }
}