package br.ucs.horus.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

	public List<Question> getQuestionsForMinorRequirement(int skillId, float userCapacity) {
		@SuppressWarnings("unchecked")
		List<Question> questions = em.createQuery("SELECT q FROM Question q WHERE q.deletedAt IS NULL"
				+ " AND q.id IN (SELECT qs.question_id FROM QuestionSkill qs WHERE qs.deletedAt IS NULL"
				+ " AND qs.skill_id = :skill_id"
				+ " AND qs.level >= :minor"
				+ " AND qs.level <= :max"
				+ " ORDER BY qs.level ASC)")
				.setParameter("skill_id", skillId)
				.setParameter("minor", userCapacity)
				.setParameter("max", userCapacity + 2) //1
				.getResultList();
		
		return questions;
	}

	public List<Question> getQuestionsNotAnswered(List<Question> questions, User user) {
		@SuppressWarnings("unchecked")
		List<Question> result = em.createQuery("SELECT q FROM Question q WHERE q.deletedAt IS NULL"
				+ " AND q.id NOT IN (SELECT h.question_id FROM Historic h WHERE h.deletedAt IS NULL"
				+ " AND h.user_id = :user_id"
				+ " AND h.question_id IN (:ids))"
				+ " AND q.id IN (:ids)")
				.setParameter("user_id", user.getId())
				.setParameter("ids", getIds(questions))
				.getResultList();
		
		return Utils.isEmpty(result) ? null : result;
	}
	
	private List<Integer> getIds(List<Question> questions) {
		final List<Integer> result = new ArrayList<>();
		if (!Utils.isEmpty(questions)) {
			for (Question question: questions) {
				result.add(question.getId());
			}
		}
		
		return result;
	}
}
