package br.ucs.horus.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ucs.horus.models.ImageMedia;
import br.ucs.horus.models.Media;
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
		List<ImageMedia> images = em.createQuery("SELECT p FROM ImageMedia p WHERE"
				+ " EXISTS (select m.id FROM Media m WHERE m.deletedAt IS NULL and m.type = :type AND "
				+ " EXISTS (select a.id FROM AuxiliaryMedia a where a.deletedAt IS NULL AND a.question_id = :question_id))")
				.setMaxResults(1)
				.setParameter("question_id", question.getId())
				.setParameter("type", Type.IMAGE.value)
				.getResultList();
		
		return Utils.isEmpty(images) ? null : images.get(0).getPath();
	}
	
	@SuppressWarnings("unchecked")
	public List<? super Media> findRecommendedMedia(Question question) {
		final ArrayList<? super Media> finalList = new ArrayList<>();
		
		List<Media> media = em.createQuery("SELECT m FROM Media m WHERE m.deletedAt IS NULL AND"
				+ " m.id IN (select r.id FROM RecommendedMedia r WHERE r.deletedAt IS NULL AND r.question_id = :question_id)")
				.setParameter("question_id", question.getId())
				.getResultList();
		
		final HashMap<Integer, List<Integer>> types = new HashMap<>(media.size());
		for (Media m: media) {
			if (!types.containsKey(m.getType())) {
				types.put(m.getType(), new ArrayList<Integer>());
			}
			
			types.get(m.getType()).add(m.getId());
		}
		
		for (Entry<Integer, List<Integer>> entry : types.entrySet()) {
			Collection<? extends Media> innerMedia = em.createQuery("SELECT m FROM " + Type.getTableName(entry.getKey()) + " m WHERE"
					+ " m.id IN :ids")
					.setParameter("ids", entry.getValue())
					.getResultList();
			
		    if (innerMedia != null) finalList.addAll(innerMedia);
		}
		
		return finalList;
	}
}
