package br.ucs.horus.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.ucs.horus.models.User;

@Stateless
@LocalBean
public class UserDAO {
	public User findUser(String user) {
		return new User();
	}
}
