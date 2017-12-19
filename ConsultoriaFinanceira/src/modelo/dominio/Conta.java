package modelo.dominio;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tab_contas")
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_CONTA")
	@SequenceGenerator(name = "ID_CONTA", sequenceName = "SEQ_CONTA", allocationSize = 1)
	private Integer codigo;
	
	@Column
	private String numeroConta;
	@Column
	private String banco;
	@Column
	private Double saldo;

	@OneToMany(mappedBy = "conta", fetch = FetchType.LAZY)
	private List<Cliente> clientes;
	
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer id) {
		this.codigo = id;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	
		public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	@Override
	public String toString() {
		return "Conta [codigo=" + codigo + ", numeroConta=" + numeroConta + 
				", banco=" + banco + ", saldo=" + saldo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
