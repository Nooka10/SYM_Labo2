package SymComManager;

import SymComManager.Objects.Person;
import android.util.Xml;
import okhttp3.Headers;
import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;


/**
 * Classe gérant la communication avec le serveur pour le fragment XMLObjectSendFragment.
 */
public class XmlObjectSymComManager {
	private CommunicationEventListener communicationEventListener = null;
	
	/**
	 * Crée un XML valide contenant les informations de la personne reçue en paramètre.
	 * @param person, la personne qu'on souhaite transmettre en XML.
	 * @return un String contenant les données de la personne au format XML.
	 */
	private String createXML(Person person) {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		
		try {
			serializer.processingInstruction("xml version='1.0' encoding='UTF-8'");
			serializer.docdecl("directory SYSTEM \"http://sym.iict.ch/directory.dtd\"");
			serializer.setOutput(writer);
			serializer.startTag("", "directory");
			serializer.startTag("", "person");
			serializer.startTag("", "name");
			serializer.text(person.getLastname());
			serializer.endTag("", "name");
			
			serializer.startTag("", "firstname");
			serializer.text(person.getFirstname());
			serializer.endTag("", "firstname");
			
			if (person.getMiddlename() != null && !person.getMiddlename().isEmpty()) {
				// la personne possède un middlename -> on ajoute la balise et les données
				serializer.startTag("", "middlename");
				serializer.text(person.getMiddlename());
				serializer.endTag("", "middlename");
			}
			
			serializer.startTag("", "gender");
			serializer.text(String.valueOf(person.getSex()));
			serializer.endTag("", "gender");
			
			serializer.startTag("", "phone");
			serializer.attribute("", "type", String.valueOf(person.getPhoneType()));
			serializer.text(person.getPhone());
			serializer.endTag("", "phone");
			
			serializer.endTag("", "person");
			serializer.endTag("", "directory");
			serializer.endDocument();
			System.out.println(writer.toString());
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Envoit une requête avec le contenu 'content' au serveur situé à l'url 'url' reçus en paramètres.
	 * @param personObjectToSend, le contenu de la requête à envoyer.
	 * @param url, l'url du serveur auquel envoyer la requête.
	 * @throws IllegalStateException, lève une exception si le communicationEventListener n'a pas été défini avant l'appel à sendRequest().
	 */
	public void sendRequest(Person personObjectToSend, String url) {
		if (communicationEventListener == null) {
			throw new IllegalStateException("You have to call setCommunicationEventListener(CommunicationEventListener l) first to be allowed to send a request.");
		}
		
		// on définit les entêtes http nécessaires pour cette requête
		Headers.Builder headersBuilder = new Headers.Builder();
		headersBuilder.add("content-type", "application/xml");
		
		// on transforme la personne reçue en paramètre en un string contenant les données de la personne au format XML
		String xml = createXML(personObjectToSend);
		
		// on crée l'Asynktask qui enverra la requête et attendra la réponse sur un thread parallèle.
		// La réponse sera traitée à l'aide du communicationEventListener passé en paramètre.
		new MyAsyncTask(xml, url, "application/xml; charset=utf-8", headersBuilder.build(), false, communicationEventListener).execute();
	}
	
	/**
	 * Défini le CommunicationEventListener à utiliser lorsque l'on re4oit la réponse du serveur.
	 * @param l, le communicationEventListener à utiliser.
	 */
	public void setCommunicationEventListener(CommunicationEventListener l) {
		communicationEventListener = l;
	}
}
