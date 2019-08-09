package br.ucs.horus.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import br.ucs.horus.models.User;

@Stateless
@LocalBean
public class UserDAO {
	@PersistenceContext(unitName = "HorusJPA")
	private EntityManager em;
	
	public User findUser(String user) {
//		User tarefa = new User();
//        tarefa.setEmail("teste@teste.com");
//        tarefa.setFirstName("Teste");
//        tarefa.setLastName("Teste1");
//
//        em.persist(tarefa);
		
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("HorusJPA");
//		@SuppressWarnings("unchecked")
//		List<User> listPersons = em.createQuery("SELECT p FROM User p").getResultList();
//        EntityManager manager = factory.createEntityManager();
//
//        manager.close();
//        factory.close();
		
		
        @SuppressWarnings("unchecked")
		List<User> listPersons = em.createQuery("SELECT p FROM User p").getResultList();
		//User user1 = em.find(User.class, 1);
		return listPersons.get(0);
	}
}
