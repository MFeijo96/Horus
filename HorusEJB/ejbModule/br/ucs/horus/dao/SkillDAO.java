package br.ucs.horus.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ucs.horus.models.Question;
import br.ucs.horus.models.QuestionSkill;
import br.ucs.horus.models.Skill;
import br.ucs.horus.utils.Utils;

@Stateless
@LocalBean
public class SkillDAO {
	@PersistenceContext(unitName = "HorusJPA")
	private EntityManager em;
	
	public List<QuestionSkill> getPositiveSkills(Question question) {
		@SuppressWarnings("unchecked")
		final List<QuestionSkill> skills = em.createQuery("SELECT qs FROM QuestionSkill qs WHERE qs.deletedAt IS NULL"
				+ " AND qs.question_id = :question_id "
				+ " AND qs.level > 0")
		.setParameter("question_id", question.getId())
		.getResultList();
		
		return Utils.isEmpty(skills) ? null : skills;
	}
	
	public List<Skill> getSkillsExcept(List<Integer> skillIds) {
		@SuppressWarnings("unchecked")
		final List<Skill> skills = em.createQuery("SELECT s FROM Skill s WHERE s.deletedAt IS NULL"
				+ " AND s.id NOT IN (:ids)")
		.setParameter("ids", skillIds)
		.getResultList();
		
		return skills == null ? new ArrayList<>(): skills;
	}
	
	public List<Skill> getSkills(List<Integer> skillIds) {
		@SuppressWarnings("unchecked")
		final List<Skill> skills = em.createQuery("SELECT s FROM Skill s WHERE s.deletedAt IS NULL"
				+ " AND s.id NOT (:ids)")
		.setParameter("ids", skillIds)
		.getResultList();
		
		return skills == null ? new ArrayList<>(): skills;
	}
	
	public List<QuestionSkill> getSkills(Question question) {
		@SuppressWarnings("unchecked")
		final List<QuestionSkill> skills = em.createQuery("SELECT qs FROM QuestionSkill qs WHERE qs.deletedAt IS NULL"
				+ " AND qs.question_id = :question_id")
		.setParameter("question_id", question.getId())
		.getResultList();
		
		return Utils.isEmpty(skills) ? null : skills;
	}
}
