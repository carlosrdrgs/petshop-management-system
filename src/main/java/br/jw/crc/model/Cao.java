package br.jw.crc.model;

public class Cao {
	private int id;
	private String nome;
	private String raca;
	private Porte porte;
	
	public Cao() {
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRaca() {
		return raca;
	}
	public void setRaca(String raca) {
		this.raca = raca;
	}
	public Porte getPorte() {
		return porte;
	}
	public void setPorte(Porte porte) {
		this.porte = porte;
	}
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Cao [id=" + id + ", nome=" + nome + ", raca=" + raca + ", porte=" + porte + "]";
	}
}
