package br.jw.crc.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Agendamento {
	private int id;
	private LocalDate dataAgendamento;
	private Cliente cliente;
	private Cao cao;
	private List<Servico> servicos;
	private Status status;
	
	private LocalDate dataExecucao;
	private double valorTotal;
	private boolean descontoAplicado;
	
	public LocalDate getDataExecucao() {
		return dataExecucao;
	}

	public java.sql.Date getDataExecucaoForJSTL() {
	    if (this.dataExecucao != null) {
	        return java.sql.Date.valueOf(this.dataExecucao);
	    }
	    return null;
	}
	
	public void setDataExecucao(LocalDate dataExecucao) {
		this.dataExecucao = dataExecucao;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public boolean isDescontoAplicado() {
		return descontoAplicado;
	}

	public void setDescontoAplicado(boolean descontoAplicado) {
		this.descontoAplicado = descontoAplicado;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Agendamento() {
		this.servicos = new ArrayList<>();
	}

	public LocalDate getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(LocalDate dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cao getCao() {
		return cao;
	}

	public void setCao(Cao cao) {
		this.cao = cao;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public int getId() {
		return id;
	}
	
	public java.sql.Date getDataAgendamentoForJSTL() {
	    if (this.dataAgendamento != null) {
	        return java.sql.Date.valueOf(this.dataAgendamento);
	    }
	    return null;
	}

	@Override
	public String toString() {
		return "Agendamento [id=" + id + ", dataAgendamento=" + dataAgendamento + ", cliente=" + cliente + ", cao="
				+ cao + ", servicos=" + servicos + "]";
	}
}
