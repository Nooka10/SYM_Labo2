package SymComManager;


import okhttp3.Headers;

public class AsyncSymComManager {
	protected CommunicationEventListener communicationEventListener = null;
	
	public void sendRequest(String content, String url) throws Exception {
		//Envoi de la requête contenant le texte saisi par l'utilisateur au serveur
		if (communicationEventListener == null) {
			throw new Exception("You have to call setCommunicationEventListener(CommunicationEventListener l) first to be allowed to send a request.");
		}
		
		Headers.Builder headersBuilder = new Headers.Builder();
		headersBuilder.add("content-type", "plain/text");
		
		new MyAsyncTask(content, url, "text/plain; charset=utf-8", headersBuilder.build(), false,  communicationEventListener).execute();
	}
	
	public void setCommunicationEventListener(CommunicationEventListener l) {
		communicationEventListener = l;
	}
}
