package br.ucs.horus.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ucs.horus.models.User;

@Stateless
@LocalBean
public class UserDAO {
	@PersistenceContext(unitName = "HorusJPA")
	private EntityManager em;

	public User findUser(String user) {
		@SuppressWarnings("unchecked")
		List<User> listPersons = em.createQuery("SELECT p FROM User p").getResultList();
		// User user1 = em.find(User.class, 1);
		return listPersons.get(0);
	}
}
