package SymComManager.Objects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Classe définissant un post, utilisée dans la partie GraphQL.
 */
public class Post {
	private final String title;
	private final String description;
	private final String content;
	private Date date;
	private final DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.FRANCE);
	
	/**
	 * Constructeur.
	 * @param title, le titre du post.
	 * @param description, la description du post.
	 * @param content, le contenu du post.
	 * @param dateString, la date de création du post.
	 */
	public Post(String title, String description, String content, String dateString) {
		this.title = title;
		this.description = description;
		this.content = content;
		
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
			this.date = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getContent() {
		return content;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getDateString() {
		return format.format(date);
	}
}
