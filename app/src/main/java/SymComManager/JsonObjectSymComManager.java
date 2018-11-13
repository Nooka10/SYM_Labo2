package SymComManager;

import SymComManager.Objects.Computer;
import com.google.gson.Gson;
import okhttp3.Headers;

public class JsonObjectSymComManager {
	private Gson gson = new Gson();
	private CommunicationEventListener communicationEventListener = null;
	
	public String createComputerObject(String computerName, String computerManufacturer) {
		Computer computer = new Computer(computerName, computerManufacturer);
		return gson.toJson(computer);
	}
	
	public void sendRequest(String jsonObject, String url) throws Exception {
		//Envoi de la requête contenant le texte saisi par l'utilisateur au serveur
		if (communicationEventListener == null) {
			throw new Exception("You have to call setCommunicationEventListener(CommunicationEventListener l) first to be allowed to send a request.");
		}
		
		Headers.Builder headersBuilder = new Headers.Builder();
		headersBuilder.add("content-type", "application/json")
				.add("accept", "application/json");
		
		new MyAsyncTask(jsonObject, url, "application/json; charset=utf-8", headersBuilder.build(), communicationEventListener).execute();
	}
	
	public void setCommunicationEventListener(CommunicationEventListener l) {
		communicationEventListener = l;
	}
}
