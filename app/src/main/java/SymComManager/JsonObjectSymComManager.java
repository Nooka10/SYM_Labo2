package SymComManager;

import SymComManager.Objects.Computer;
import com.google.gson.Gson;
import okhttp3.Headers;

/**
 * Classe gérant la communication avec le serveur pour le fragment JsonObjectSendFragment.
 */
public class JsonObjectSymComManager {
	private Gson gson = new Gson();
	protected CommunicationEventListener communicationEventListener = null;
	
	/**
	 * Crée un objet Computer à partir des paramètres reçus et le transforme en Json.
	 * @param computerName, le nom de l'ordinateur.
	 * @param computerManufacturer, le constructeur de l'ordinateur.
	 * @return l'objet Computer créé au format Json.
	 */
	public String createComputerObject(String computerName, String computerManufacturer) {
		Computer computer = new Computer(computerName, computerManufacturer);
		return gson.toJson(computer);
	}
	
	/**
	 * Envoit une requête avec le contenu 'content' au serveur situé à l'url 'url' reçus en paramètres.
	 * @param jsonObject, le contenu de la requête à envoyer.
	 * @param url, l'url du serveur auquel envoyer la requête.
	 * @throws IllegalStateException, lève une exception si le communicationEventListener n'a pas été défini avant l'appel à sendRequest().
	 */
	public void sendRequest(String jsonObject, String url) throws Exception {
		if (communicationEventListener == null) {
			throw new IllegalStateException("You have to call setCommunicationEventListener(CommunicationEventListener l) first to be allowed to send a request.");
		}
		
		// on définit les entêtes http nécessaires pour cette requête
		Headers.Builder headersBuilder = new Headers.Builder();
		headersBuilder.add("content-type", "application/json")
				.add("accept", "application/json");
		
		// on crée l'Asynktask qui enverra la requête et attendra la réponse sur un thread parallèle.
		// La réponse sera traitée à l'aide du communicationEventListener passé en paramètre.
		new MyAsyncTask(jsonObject, url, "application/json; charset=utf-8", headersBuilder.build(), false, communicationEventListener).execute();
	}
	
	/**
	 * Défini le CommunicationEventListener à utiliser lorsque l'on re4oit la réponse du serveur.
	 * @param l, le communicationEventListener à utiliser.
	 */
	public void setCommunicationEventListener(CommunicationEventListener l) {
		communicationEventListener = l;
	}
}
