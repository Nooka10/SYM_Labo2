package SymComManager;

import okhttp3.Headers;

/**
 * Classe gérant la communication avec le serveur pour le fragment AsyncSendFragment.
 */
public class AsyncSymComManager {
	
	private CommunicationEventListener communicationEventListener = null;
	
	/**
	 * Envoit une requête avec le contenu 'content' au serveur situé à l'url 'url' reçus en paramètres.
	 * @param content, le contenu de la requête à envoyer.
	 * @param url, l'url du serveur auquel envoyer la requête.
	 * @throws IllegalStateException, lève une exception si le communicationEventListener n'a pas été défini avant l'appel à sendRequest().
	 */
	public void sendRequest(String content, String url) throws Exception {
		if (communicationEventListener == null) {
			throw new IllegalStateException("You have to call setCommunicationEventListener(CommunicationEventListener l) first to be allowed to send a request.");
		}
		
		// on définit les entêtes http nécessaires pour cette requête
		Headers.Builder headersBuilder = new Headers.Builder();
		headersBuilder.add("content-type", "plain/text");
		
		// on crée l'Asynktask qui enverra la requête et attendra la réponse sur un thread parallèle.
		// La réponse sera traitée à l'aide du communicationEventListener passé en paramètre.
		new MyAsyncTask(content, url, "text/plain; charset=utf-8", headersBuilder.build(), false,  communicationEventListener).execute();
	}
	
	/**
	 * Défini le CommunicationEventListener à utiliser lorsque l'on re4oit la réponse du serveur.
	 * @param l, le communicationEventListener à utiliser.
	 */
	public void setCommunicationEventListener(CommunicationEventListener l) {
		communicationEventListener = l;
	}
}
