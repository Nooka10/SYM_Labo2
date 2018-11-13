package SymComManager;

import SymComManager.Objects.Author;

public class GraphQLObjectSymComManager extends JsonObjectSymComManager {
	public void getAllAuthors(){
		String query = "{\"query\":\"{allAuthors{id first_name last_name }}\"}";
		try {
			sendRequest(query, "http://sym.iict.ch/api/graphql");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getAllAuthorsPosts(Author author) {
		String query = "{\"query\":\"{allPostByAuthor(authorId:" + author.getId() + "){title description content date}}\"}";
		try {
			sendRequest(query, "http://sym.iict.ch/api/graphql");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
