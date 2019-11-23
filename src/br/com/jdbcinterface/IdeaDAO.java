package br.com.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.objects.Idea;

public interface IdeaDAO
{
	public boolean addIdea(Idea idea) throws SQLException;
	public List<Idea> searchIdeaByUser(int idUser, int status) throws SQLException;
	public Idea searchIdeaById(int idIdea, int idStatus) throws SQLException;
	public List<Idea> searchIdeaByStatus(int status) throws SQLException;
	public boolean setScore(int idUser, int score) throws SQLException;
}