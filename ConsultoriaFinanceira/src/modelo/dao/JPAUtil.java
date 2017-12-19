package modelo.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static EntityManagerFactory financeira;

	static {
		JPAUtil.financeira = Persistence.createEntityManagerFactory("banco");
	}

	public static EntityManagerFactory getFactory() {
		return JPAUtil.financeira;
	}

	private static ThreadLocal<EntityManager> CACHE = new ThreadLocal<EntityManager>();

	public static void limparCacheEntityManager() {
		
		EntityManager em = CACHE.get();

		
		if (em != null)
			em.close();

		CACHE.remove();
	}

	public static EntityManager getEntityManager() {
		
		EntityManager retorno = CACHE.get();

				if ((retorno != null) && (!retorno.isOpen()))
			retorno = null;

		
		if (retorno == null) {
			retorno = JPAUtil.financeira.createEntityManager();
			
			CACHE.set(retorno);
		}

		return retorno;
	}

}
