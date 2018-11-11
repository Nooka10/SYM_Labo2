package SymComManager.Objects;

/**
 * Modèle de données utilisé pour la transmission d'objet JSON
 */
public class Person {
	private String firstname;
	private String name;
	private String middlename;
	private Enum gender;
	private String phone;
	
	public Person(String firstname, String name, String middlename, Enum gender, String phone) {
		this.firstname = firstname;
		this.name = name;
		this.middlename = middlename;
		this.gender = gender;
		this.phone = phone;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMiddlename() {
		return middlename;
	}
	
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	
	public Enum getGender() {
		return gender;
	}
	
	public void setGender(Enum gender) {
		this.gender = gender;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
}

