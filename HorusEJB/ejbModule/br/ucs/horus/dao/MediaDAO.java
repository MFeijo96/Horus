package br.ucs.horus.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ucs.horus.models.ImageMedia;
import br.ucs.horus.models.Media.Type;
import br.ucs.horus.models.Question;
import br.ucs.horus.utils.Utils;

@Stateless	
@LocalBean
public class MediaDAO {
	@PersistenceContext(unitName = "HorusJPA")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public String findAuxiliaryMediaName(Question question) {
		List<ImageMedia> images = em.createQuery("SELECT p FROM ImageMedia p where"
				+ " EXISTS (select m.id FROM Media m WHERE m.deletedAt IS NULL and m.type = :type AND "
				+ " EXISTS (select a.id FROM AuxiliaryMedia a where a.deletedAt IS NULL AND a.question_id = :question_id))")
				.setMaxResults(1)
				.setParameter("question_id", question.getId())
				.setParameter("type", Type.IMAGE.value)
				.getResultList();
		
		return Utils.isEmpty(images) ? null : images.get(0).getPath();
	}
}
