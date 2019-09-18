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

	public User findUser(String email) {
		@SuppressWarnings("unchecked")
		List<User> listPersons = em.createQuery("SELECT p FROM User p WHERE p.email = :email AND p.deletedAt IS NULL")
		.setParameter("email", email)
		.getResultList();
		return listPersons.get(0);
	}
	
	public void update(User user) {
		em.merge(user);
	}
}
