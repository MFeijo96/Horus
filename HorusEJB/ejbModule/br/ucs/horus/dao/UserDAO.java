package br.ucs.horus.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ucs.horus.models.User;
import br.ucs.horus.models.UserSkill;
import br.ucs.horus.utils.Utils;

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
	
	public void updateSkill(UserSkill userSkill, boolean alreadyExists) {
		if (alreadyExists) em.merge(userSkill);
		else em.persist(userSkill);
	}
	
	public void updateUser(User user) {
		em.merge(user);
	}

	public List<UserSkill> getUserSkills(User user) {
		@SuppressWarnings("unchecked")
		final List<UserSkill> skills = em.createQuery("SELECT us FROM UserSkill us WHERE us.deletedAt IS NULL"
				+ " AND us.user_id = :user_id"
				+ " ORDER BY us.level ASC")
		.setParameter("user_id", user.getId())
		.getResultList();
		
		return Utils.isEmpty(skills) ? null : skills;
	}
}
