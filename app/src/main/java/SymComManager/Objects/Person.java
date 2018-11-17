package SymComManager.Objects;

import android.support.annotation.NonNull;

/**
 * Classe définissant une personne, utilisée dans la partie XML.
 */
public class Person {
	private String firstname;
	private String lastname;
	private String middlename;
	private String phone;
	private Sex sex;
	private PhoneType phoneType;
	
	/**
	 * Énumération des sex valides.
	 */
	private enum Sex {
		MALE("male"),
		FEMALE("female");
		
		private final String sex;
		
		Sex(String sex) {
			this.sex = sex;
		}
		
		@NonNull
		@Override
		public String toString() {
			return sex;
		}
	}
	
	/**
	 * Énumération des types de numéro de téléphone valides
	 */
	private enum PhoneType {
		HOME("home"),
		WORK("work"),
		MOBILE("mobile");
		
		private final String phoneType;
		
		PhoneType(String phoneType) {
			this.phoneType = phoneType;
		}
		
		@NonNull
		@Override
		public String toString() {
			return phoneType;
		}
	}
	
	/**
	 * Constructeur.
	 * @param firstname, le prénom de la personne.
	 * @param lastname, le nom de la personne.
	 * @param isMale, le sexe de la personne. Vaut true si c'est un homme, false sinon.
	 * @param middlename, le 2ème prénom de la personne.
	 * @param phonenumber, le numéro de téléphone de la personne.
	 * @param phonetype, le type du numéro de téléphone de la personne. Doit être l'une des valeurs suivantes : ["home", "work", "mobile"]
	 */
	public Person(String firstname, String lastname, boolean isMale, String middlename, String phonenumber, String phonetype) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.middlename = middlename;
		this.sex = isMale ? Person.Sex.MALE : Person.Sex.FEMALE;
		this.phone = phonenumber;
		
		switch (phonetype) {
			case "home":
				this.phoneType = Person.PhoneType.HOME;
				break;
			case "work":
				this.phoneType = Person.PhoneType.WORK;
				break;
			case "mobile":
				this.phoneType = Person.PhoneType.MOBILE;
				break;
			default:
				throw new IllegalArgumentException("Unknown enum value for Person.PhoneType. Should be 'home', 'work' or 'mobile'. Received : " + phonetype);
		}
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String name) {
		this.lastname = name;
	}
	
	public String getMiddlename() {
		return middlename;
	}
	
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Sex getSex() {
		return sex;
	}
	
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	public PhoneType getPhoneType() {
		return phoneType;
	}
	
	public void setPhoneType(PhoneType phoneType) {
		this.phoneType = phoneType;
	}
}

