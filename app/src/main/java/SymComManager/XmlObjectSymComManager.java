package SymComManager;

import SymComManager.Objects.Person;
import okhttp3.Headers;

public class XmlObjectSymComManager {
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
	
	public void sendRequest(Person personObjectToSend, String url) throws Exception {
		//Envoi de la requête contenant le texte saisi par l'utilisateur au serveur
		
		if (communicationEventListener == null) {
			throw new Exception("You have to call setCommunicationEventListener(CommunicationEventListener l) first to be allowed to send a request.");
		}
		
		Headers.Builder headersBuilder = new Headers.Builder();
		headersBuilder.add("content-type", "application/xml");
		
		String xml = createXML(personObjectToSend);
		new MyAsyncTask(xml, url, "application/xml; charset=utf-8", headersBuilder.build(), false, communicationEventListener).execute();
	}
	
	public void setCommunicationEventListener(CommunicationEventListener l) {
		communicationEventListener = l;
	}
}
