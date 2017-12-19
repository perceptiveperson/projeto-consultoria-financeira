package modelo.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import modelo.dominio.Cliente;
import modelo.dominio.Conta;

public class ContaDAO extends JpaDAO<Conta> {
	
	public List<Conta> filtrarContas(Conta conta, String numero) {
		
		System.out.println(conta + "__" + numero);
		String jpql = "from Conta c ";

		if (conta != null) {
			jpql = jpql + " where c.banco = :ban";
		} else if (numero != null) {
			jpql = jpql + " where c.numeroConta like :num";
		}

		jpql = jpql + " order by c.banco";

		TypedQuery<Conta> comando = this.getEntityManager().createQuery(jpql, Conta.class);

		if (conta != null)
			comando.setParameter("ban",  conta.getBanco());
		else if (numero != null)
			comando.setParameter("num", "%" + numero + "%");
		System.out.println(comando.getResultList().toString());
		return comando.getResultList();
	}
	
	public List<Cliente> lerClientes(Integer codigo) {
		String jpql = "from Cliente c where c.conta.codigo = :cont";
		jpql += " order by c.codigo";
		
		TypedQuery<Cliente> comando = this.getEntityManager().createQuery(jpql, Cliente.class);
		comando.setParameter("cont", codigo);
		
		return comando.getResultList();
	}

}
