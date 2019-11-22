package br.com.objects;

import java.io.Serializable;

public class Idea implements Serializable
{
	private static final long serialVersionUID=1L;
	
	private int id;
	private int status;
	private String registerDate;
	private String title;
	private String description;
	private int userIdentification;
	private int likes;
	private int comments;
	private int idApprover;
	private String nameApprover;
	private String expectedResults;
	private String observationApproval;
	private int idCompanyUnit;
	private String nameCompanyUnit;
	private int idDepartment;
	private String nameDepartment;
	private int idAuthor;
	private String nameAuthor;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getRegisterDate() {
		return registerDate;
	}
	
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getUserIdentification() {
		return userIdentification;
	}
	
	public void setUserIdentification(int userIdentification) {
		this.userIdentification = userIdentification;
	}
	
	public int getLikes() {
		return likes;
	}
	
	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	public int getComments() {
		return comments;
	}
	
	public void setComments(int comments) {
		this.comments = comments;
	}
	
	public int getIdApprover() {
		return idApprover;
	}
	
	public void setIdApprover(int idApprover) {
		this.idApprover = idApprover;
	}
	
	public String getNameApprover() {
		return nameApprover;
	}
	
	public void setNameApprover(String nameApprover) {
		this.nameApprover = nameApprover;
	}
	
	public String getExpectedResults() {
		return expectedResults;
	}
	
	public void setExpectedResults(String expectedResults) {
		this.expectedResults = expectedResults;
	}
	
	public String getObservationApproval() {
		return observationApproval;
	}
	
	public void setObservationApproval(String observationApproval) {
		this.observationApproval = observationApproval;
	}
	
	public int getIdCompanyUnit() {
		return idCompanyUnit;
	}
	
	public void setIdCompanyUnit(int idCompanyUnit) {
		this.idCompanyUnit = idCompanyUnit;
	}
	
	public String getNameCompanyUnit() {
		return nameCompanyUnit;
	}
	
	public void setNameCompanyUnit(String nameCompanyUnit) {
		this.nameCompanyUnit = nameCompanyUnit;
	}
	
	public int getIdDepartment() {
		return idDepartment;
	}
	
	public void setIdDepartment(int idDepartment) {
		this.idDepartment = idDepartment;
	}
	
	public String getNameDepartment() {
		return nameDepartment;
	}
	
	public void setNameDepartment(String nameDepartment) {
		this.nameDepartment = nameDepartment;
	}
	
	public int getIdAuthor() {
		return idAuthor;
	}
	
	public void setIdAuthor(int idAuthor) {
		this.idAuthor = idAuthor;
	}
	
	public String getNameAuthor() {
		return nameAuthor;
	}
	
	public void setNameAuthor(String nameAuthor) {
		this.nameAuthor = nameAuthor;
	}
}