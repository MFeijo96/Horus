package br.ucs.horus.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ucs.horus.models.Answer;
import br.ucs.horus.models.Historic;
import br.ucs.horus.models.Question;
import br.ucs.horus.models.User;
import br.ucs.horus.utils.Utils;

@Stateless
@LocalBean
public class QuestionDAO {
	@PersistenceContext(unitName = "HorusJPA")
	private EntityManager em;

	public Question findQuestion(int id) {
		return em.find(Question.class, id);
	}

	public Historic getLastAnswer(Question question, User user) {
		@SuppressWarnings("unchecked")
		List<Historic> historics = em.createQuery("SELECT h FROM Historic h WHERE h.deletedAt IS NULL AND"
				+ " h.user_id = :user_id AND h.question_id = :question_id ORDER BY h.id DESC")
				.setParameter("question_id", question.getId())
				.setParameter("user_id", user.getId())
				.setMaxResults(1)
				.getResultList();
		
		return Utils.isEmpty(historics) ? null : historics.get(0);
	}

	public int getTotalAvailableQuestions() {
		Long count = (Long) em.createQuery("SELECT count(q.id) FROM Question q WHERE q.deletedAt IS NULL").getSingleResult();
		return count.intValue();
	}
	
	public void saveHistoric(Historic historic) {
		em.persist(historic);
	}
}
