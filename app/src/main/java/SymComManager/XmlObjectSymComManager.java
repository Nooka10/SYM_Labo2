package SymComManager;

import SymComManager.Objects.Person;
import android.support.annotation.NonNull;
import okhttp3.*;

import java.io.IOException;

public class XmlObjectSymComManager {
	private SymComManager scm = SymComManager.getInstance();
	
	private CommunicationEventListener communicationEventListener = null;
	
	private String createXML(Person person) {
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version='1.0' encoding='UTF-8'?>\n")
				.append("<!DOCTYPE directory SYSTEM \"http://sym.iict.ch/directory.dtd\">")
				.append("<directory>")
				.append("   <person>")
				.append("       <name>").append(person.getLastname()).append("</name>")
				.append("       <firstname>").append(person.getFirstname()).append("</firstname>");
		
		if (person.getMiddlename() != null && !person.getMiddlename().isEmpty()) {
			xml.append("       <middlename>").append(person.getMiddlename()).append("</middlename>");
		}
		xml.append("       <gender>").append(person.getSex()).append("</gender>")
				.append("       <phone type='").append(person.getPhoneType()).append("'>").append(person.getPhone()).append("</phone>")
				.append("   </person>")
				.append("</directory>");
		
		return xml.toString();
	}
	
	// FIXME: pourquoi SendRequest doit retourner un String ? Est-ce obligatoire ?
	public void sendRequest(Person personObjectToSend, String url) throws Exception {
		//Envoi de la requête contenant le texte saisi par l'utilisateur au serveur
		
		if (communicationEventListener == null) {
			throw new Exception("You have to call setCommunicationEventListener(CommunicationEventListener l) first to be allowed to send a request.");
		}
		
		String xml = createXML(personObjectToSend);
		
		Headers.Builder headersBuilder = new Headers.Builder();
		headersBuilder.add("content-type", "application/xml");
		
		scm.sendRequest(xml, url, "application/xml; charset=utf-8", headersBuilder.build(), new Callback() {
			@Override
			public void onFailure(@NonNull Call call, @NonNull IOException e) {
				e.printStackTrace();
			}
			
			@Override
			public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
				try (ResponseBody responseBody = response.body()) {
					if (!response.isSuccessful()) {
						throw new IOException("Unexpected code " + response);
					} else {
						communicationEventListener.handleServerResponse(responseBody.string());
					}
				}
			}
		});
	}
	
	public void setCommunicationEventListener(CommunicationEventListener l) {
		communicationEventListener = l;
	}
}
