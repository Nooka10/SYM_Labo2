package SymComManager;

import SymComManager.Objects.Computer;
import android.support.annotation.NonNull;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class JsonObjectSymComManager {
	private SymComManager scm = SymComManager.getInstance();
	private Gson gson = new Gson();
	
	private CommunicationEventListener communicationEventListener = null;
	
	public String createComputerObject(String computerName, String computerManufacturer) {
		Computer computer = new Computer(computerName, computerManufacturer);
		return gson.toJson(computer);
	}
	
	// FIXME: pourquoi SendRequest doit retourner un String ? Est-ce obligatoire ?
	public void sendRequest(String jsonObject, String url) throws Exception {
		//Envoi de la requête contenant le texte saisi par l'utilisateur au serveur
		
		// FIXME: Comment prendre en charge les erreur? -> afficher une popup, ne rien faire.....?
		if (communicationEventListener == null) {
			throw new Exception("You have to call setCommunicationEventListener(CommunicationEventListener l) first to be allowed to send a request.");
		}
		
		Headers.Builder headersBuilder = new Headers.Builder();
		headersBuilder.add("content-type", "application/json")
				.add("accept", "application/json");
		
		scm.sendRequest(jsonObject, url, "application/json; charset=utf-8", headersBuilder.build(), new Callback() {
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
