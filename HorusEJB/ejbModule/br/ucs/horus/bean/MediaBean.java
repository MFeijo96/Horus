package br.ucs.horus.bean;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.ucs.horus.dao.MediaDAO;
import br.ucs.horus.models.Question;
import br.ucs.horus.utils.Utils;

@Stateless
@LocalBean
public class MediaBean {
	@EJB
	private MediaDAO	mediaDAO;
	
	public String getAuxiliaryMediaPath(Question question) {
		String path = mediaDAO.findAuxiliaryMediaName(question);
		
		if (!Utils.isEmpty(path)) {
			path = "/question/auxi/" + path;
		}
		
		return path;
	}
}
