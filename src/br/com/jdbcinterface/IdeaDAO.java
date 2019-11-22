package br.com.jdbcinterface;

import java.util.List;

import br.com.objects.Idea;

public interface IdeaDAO
{
	public boolean addIdea(Idea idea);
	public List<Idea> searchIdeaByUser(int idUser, int status);
	public Idea searchIdeaById(int idIdea, int idStatus);
	public List<Idea> searchIdeaByStatus(int status);
	public boolean setScore(int idUser, int score);
}