package SymComManager.Objects;

/**
 * Modèle de données utilisé pour la transmission d'objet JSON
 */
public class Person {
	private String firstname;
	private String lastname;
	private String middlename;
	private String phone;
	private Sex sex;
	private PhoneType phoneType;
	
	private enum Sex {
		MALE("male"),
		FEMALE("female");
		
		private final String sex;
		
		Sex(String sex) {
			this.sex = sex;
		}
		
		@Override
		public String toString() {
			return sex;
		}
	}
	
	private enum PhoneType {
		HOME("home"),
		WORK("work"),
		MOBILE("mobile");
		
		private final String phoneType;
		
		PhoneType(String phoneType) {
			this.phoneType = phoneType;
		}
		
		@Override
		public String toString() {
			return phoneType;
		}
	}
	
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

