package br.ucs.horus.bean;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.ucs.horus.dao.UserDAO;
import br.ucs.horus.models.User;

@Stateless
@LocalBean
public class UserBean {

	@EJB
	private UserDAO	userDAO;
	
	public User login(String username, String password) {
		User user = userDAO.findUser(username);
		
		if (user != null && password != null && password.length() > 0) {
			if (user.getPassword().equals(password)) {
				return user;
			} else {
				return null;
			}
		}
		
		return null;
	}
}
