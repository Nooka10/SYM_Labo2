package SymComManager;

import SymComManager.Objects.Computer;
import com.google.gson.Gson;

public class GraphQLObjectSymComManager extends JsonObjectSymComManager {
	private SymComManager scm = SymComManager.getInstance();
	private Gson gson = new Gson();
	
	private CommunicationEventListener communicationEventListener = null;
	
	
	
	public void getAllAuthors(){
		String query = "{\"query\":\"{allAuthors{id first_name last_name }}\"}";
		try {
			/*
			// On set l'action qui sera effectuée lorsqu'on recevra la réponse à la requête au serveur.
			setCommunicationEventListener(new CommunicationEventListener() {
				@Override
				public boolean handleServerResponse(final String response) {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							responseTextView.setText(response);
						}
					});
					return true;
				}
			});
			
			sendRequest(query, "http://sym.iict.ch/api/graphql");
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setCommunicationEventListener(CommunicationEventListener l) {
		communicationEventListener = l;
	}
}
