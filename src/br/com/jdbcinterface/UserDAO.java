package br.com.jdbcinterface;

import java.util.List;
import br.com.objects.User;

public interface UserDAO
{
	public boolean loginValidation(User user);
	public User loggedUsername(String email);
	public List<User> searchUserByName(String searchUser);
	public User searchUserById(int idUser);
}