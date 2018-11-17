package SymComManager;

import SymComManager.Objects.Author;

/**
 * Classe gérant la communication avec le serveur pour le fragment GraphQLSendFragment.
 * Hérite de la classe JsonObjectSymComManager.
 */
public class GraphQLObjectSymComManager extends JsonObjectSymComManager {
	
	/**
	 * Envoit une requête GraphQL permettant de récupérer la liste de tous les auteurs.
	 */
	public void getAllAuthors(){
		// la Query GraphQL
		String query = "{\"query\":\"{allAuthors{id first_name last_name }}\"}";
		try {
			// on envoit la requête
			sendRequest(query, "http://sym.iict.ch/api/graphql");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Envoit une requête GraphQL permettant de récupérer les posts de l'auteur reçu en paramètre.
	 */
	public void getAllAuthorsPosts(Author author) {
		// la Query GraphQL
		String query = "{\"query\":\"{allPostByAuthor(authorId:" + author.getId() + "){title description content date}}\"}";
		try {
			// on envoit la requête
			sendRequest(query, "http://sym.iict.ch/api/graphql");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
