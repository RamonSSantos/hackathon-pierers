package br.com.objects;

import java.io.Serializable;

public class CompanyUnit implements Serializable
{
	private static final long serialVersionUID=1L;
	
	private int id;
	private String tradeName;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTradeName() {
		return tradeName;
	}
	
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
}