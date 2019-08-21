package br.ucs.horus.bean;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.ucs.horus.dao.QuestionDAO;
import br.ucs.horus.models.Question;

@Stateless
@LocalBean
public class QuestionBean {
	@EJB
	private QuestionDAO	questionDAO;
	
	public Question nextQuestion(int lastId) {
		Question question = questionDAO.findQuestion(lastId + 1);
		
		return question;
	}
}
