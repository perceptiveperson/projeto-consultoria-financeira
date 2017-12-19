package controle.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import modelo.dao.ContaDAO;
import modelo.dao.ClienteDAO;
import modelo.dominio.Conta;
import modelo.dominio.Cliente;

@ManagedBean(name = "clienteMB")
@RequestScoped
public class ClienteMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{param.codParam}")
	private String codParam;

	@ManagedProperty(value = "#{logimMB}")
	private LoginMB logimMB;

	private Cliente cliente = new Cliente();

	private ClienteDAO dao = new ClienteDAO();

	private List<Cliente> clientes = null;

	private List<Conta> contas = null;

	private UploadedFile uploadedFile;

	public String getContador() {
		String nome = this.cliente.getNome();
		if (nome == null)
			return "0";

		return String.valueOf(nome.length());
	}

	public List<Cliente> getClientes() {

		if (this.clientes == null)
			this.clientes = this.dao.lerTodos();

		return clientes;
	}

	public List<Conta> getContas() {

		return contas;
	}

	public String getCodParam() {
		return codParam;
	}

	public void setCodParam(String codParam) {
		this.codParam = codParam;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LoginMB getLogimMB() {
		return logimMB;
	}

	public void setLogimMB(LoginMB logimMB) {
		this.logimMB = logimMB;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public void lerCliente() {

		if (codParam != null) {
			try {
				Integer id = Integer.parseInt(this.codParam);
				this.cliente = this.dao.lerPorId(id);
			} catch (Exception e) {
			}
		}
	}

	public String acaoListar() {
		return "clienteListar.jsf?faces-redirect=true";
	}

	public String acaoAbrirInclusao() {

		this.cliente = new Cliente();

		return "clienteEditar.jsf";
	}

	public String acaoAbrirAlteracao(Integer codigo) {

		this.cliente = dao.lerPorId(codigo);

		return "clienteEditar.jsf";
	}

	public String acaoExcluir(Integer codigo) {

		this.cliente = dao.lerPorId(codigo);

		this.dao.excluir(this.cliente);

		return acaoListar();
	}

	public String acaoSalvar() {
		// recupera os bytes da foto
		byte[] foto = this.uploadedFile.getContents();

		if (foto != null && foto.length > 0)
			this.cliente.setFoto(foto);
		
		this.dao.salvar(this.cliente);

		return acaoListar();
	}

	public String acaoCancelar() {

		return acaoListar();
	}

	public void download() throws IOException {

		FacesContext contexto = FacesContext.getCurrentInstance();
		ExternalContext external = contexto.getExternalContext();
		OutputStream saida = external.getResponseOutputStream();

		String codigo = external.getRequestParameterMap().get("codigo");

		external.responseReset();

		external.setResponseContentType("image/jpeg");

		external.setResponseHeader("Content-Disposition", "inline; filename=foto-" + codigo + ".jpg");
		external.setResponseHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		external.setResponseHeader("Pragma", "no-cache");
		external.setResponseHeader("Expires", "0");
		Integer id = null;
		Cliente client = null;
		try {
			id = Integer.parseInt(codigo);

			client = dao.lerPorId(id);
		} catch (Exception e) {
		}

		if (client != null) {
			byte[] foto = client.getFoto();

			if (foto != null) {

				external.setResponseContentLength(foto.length);

				saida.write(foto);
			}
		}

		contexto.responseComplete();
	}
}
