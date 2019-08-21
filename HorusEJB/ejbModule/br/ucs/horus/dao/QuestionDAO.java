package br.ucs.horus.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ucs.horus.models.Question;

@Stateless
@LocalBean
public class QuestionDAO {
	@PersistenceContext(unitName = "HorusJPA")
	private EntityManager em;

	public Question findQuestion(int id) {
		return em.find(Question.class, id);
	}
}
