package SymComManager;

import okhttp3.*;

import java.io.IOException;

public class AsyncSymComManager {
	private SymComManager scm = SymComManager.getInstance();
	
	protected CommunicationEventListener communicationEventListener = null;
	
	// FIXME: pourquoi SendRequest doit retourner un String ? Est-ce obligatoire ?
	public void sendRequest(String content, String url) throws Exception {
		//Envoi de la requête contenant le texte saisi par l'utilisateur au serveur
		
		if (communicationEventListener == null) {
			throw new Exception("You have to call setCommunicationEventListener(CommunicationEventListener l) first to be allowed to send a request.");
		}
		
		Headers.Builder headersBuilder = new Headers.Builder();
		headersBuilder.add("content-type", "plain/text");
		
		scm.sendRequest(content, url, "text/plain; charset=utf-8", headersBuilder.build(), new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}
			
			@Override
			public void onResponse(Call call, final Response response) throws IOException {
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
