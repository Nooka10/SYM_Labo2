package SymComManager.Objects;

import android.support.annotation.NonNull;

/**
 * Classe définissant un auteur, utilisée dans la partie GraphQL.
 */
public class Author {
	private int id;
	private String firstname;
	private String lastname;
	
	/**
	 * Constructeur.
	 * @param id, l'id de l'auteur.
	 * @param firstname, le prénom de l'auteur.
	 * @param lastname, le nom de l'auteur.
	 */
	public Author(int id, String firstname, String lastname) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	/**
	 * Getter de l'id.
	 * @return id, un entier.
	 */
	public int getId() {
		return id;
	}
	
	@NonNull
	@Override
	public String toString() {
		return firstname + " " + lastname;
	}
}
