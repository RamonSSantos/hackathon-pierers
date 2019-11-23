package br.com.jdbcinterface;

import java.sql.SQLException;
import java.util.List;
import br.com.objects.User;

public interface UserDAO
{
	public boolean loginValidation(User user) throws SQLException;
	public User loggedUsername(String email) throws SQLException;
	public List<User> searchUserByName(String searchUser) throws SQLException;
	public User searchUserById(int idUser) throws SQLException;
}