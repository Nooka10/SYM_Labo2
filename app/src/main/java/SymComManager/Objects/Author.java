package SymComManager.Objects;

public class Author {
	private int id;
	private String firstname;
	private String lastname;
	
	public Author(int id, String firstname, String lastname) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public int getId() {
		return id;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	@Override
	public String toString() {
		return firstname + " " + lastname;
	}
}
