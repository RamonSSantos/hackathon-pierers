package br.com.objects;

import java.io.Serializable;

public class User implements Serializable
{
	private static final long serialVersionUID=1L;
	
	private int id;
	private String cpf;
	private int status;
	private String fullname;
	private String email;
	private String password;
	private String password_validation;
	private int currentScore;
	private int totalScore;
	private int profile;
	private int idCompanyUnit;
	private String nameCompanyUnit;
	private int idDepartment;
	private String nameDepartment;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getFullname() {
		return fullname;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword_validation() {
		return password_validation;
	}
	
	public void setPassword_validation(String password_validation) {
		this.password_validation = password_validation;
	}
	
	public int getCurrentScore() {
		return currentScore;
	}
	
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}
	
	public int getTotalScore() {
		return totalScore;
	}
	
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	
	public int getProfile() {
		return profile;
	}
	
	public void setProfile(int profile) {
		this.profile = profile;
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
}