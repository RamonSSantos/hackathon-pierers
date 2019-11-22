package br.com.objects;

import java.io.Serializable;

public class Reward implements Serializable
{
	private static final long serialVersionUID=1L;
	
	private int id;
	private int score;
	private String description;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}