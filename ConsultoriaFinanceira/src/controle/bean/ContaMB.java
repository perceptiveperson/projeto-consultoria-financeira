package controle.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import modelo.dao.ContaDAO;
import modelo.dominio.Cliente;
import modelo.dominio.Conta;

@ManagedBean(name = "contaMB")
@RequestScoped
public class ContaMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{param.codParam}")
	private String codParam;
	private Conta conta;
	private ContaDAO dao;
	private List<Conta> contas;
	private List<Cliente> clientes;
	private Conta filtroConta;
	private String filtroNumConta;

	@PostConstruct
	public void inicializador() {
		this.conta = new Conta();
		this.dao = new ContaDAO();
	}

	// Getters e Setters
	public String getCodParam() {
		return codParam;
	}

	public void setCodParam(String codParam) {
		this.codParam = codParam;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public List<Conta> getContas() {

		if (this.contas == null)
			this.contas = this.dao.lerTodos();
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public List<Cliente> getClientes() {
		
		if (this.clientes == null)
			this.clientes = this.dao.lerClientes(this.conta.getCodigo());
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Conta getFiltroConta() {
		return filtroConta;
	}

	public void setFiltroConta(Conta filtroConta) {
		this.filtroConta = filtroConta;
	}

	public String getFiltroNumConta() {
		return filtroNumConta;
	}

	public void setFiltroNumConta(String filtroNumConta) {
		this.filtroNumConta = filtroNumConta;
	}

	// Actions
	public String acaoPesquisar() {

		this.contas = this.dao.filtrarContas(filtroConta, filtroNumConta);

		return "contaListar.jsf";
	}

	public String acaoListar() {
		return "contaListar.jsf?faces-redirect=true";
	}

	public String acaoAbrirInclusao() {

		this.conta = new Conta();

		return "contaEditar.jsf";
	}

	public String acaoAbrirAlteracao(Integer codigo) {

		this.conta = dao.lerPorId(codigo);

		return "contaEditar.jsf";
	}

	public String acaoExcluir(Integer codigo) {

		this.conta = dao.lerPorId(codigo);

		if (this.conta.getClientes() != null) {

			FacesContext contexto = FacesContext.getCurrentInstance();
			String mensagem = contexto.getApplication().evaluateExpressionGet(contexto, "#{msgs.msgNaoExcluirConta}",
					String.class);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null);
			contexto.addMessage(null, msg);
			return null;
		}

		this.dao.excluir(this.conta);

		return acaoListar();
	}

	public String acaoSalvar() {

		this.dao.salvar(this.conta);

		return acaoListar();
	}

	public String acaoCancelar() {

		return acaoListar();
	}

	// Outros métodos
	public void lerConta() {

		if (codParam != null) {
			try {
				Integer id = Integer.parseInt(this.codParam);
				this.conta = this.dao.lerPorId(id);
			} catch (Exception e) {
			}
		}
	}
}
