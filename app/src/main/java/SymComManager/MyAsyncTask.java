package SymComManager;

import android.os.AsyncTask;
import okhttp3.Headers;
import okhttp3.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * Tâche asynchrone gérant l'envoit d'une requête, l'attente de la réponse et son traitement sur un thread à part.
 */
class MyAsyncTask extends AsyncTask<Void, String, String> {
	private final String content;
	private final String url;
	private final String mediaType;
	private final Headers headers;
	private final boolean enableCompression;
	private final SymComManager scm = SymComManager.getInstance();
	private final CommunicationEventListener communicationEventListener;
	private Exception error;
	
	/**
	 * Constructeur.
	 * @param content, le contenu de la requête à envoyer.
	 * @param url, l'url du serveur auquel envoyer la requête.
	 * @param mediaType, le MediaType du body de la requête à envoyer.
	 * @param headers, les headers HTTP avec lesquels envoyer la requête.
	 * @param enableCompression, si true, active la compression Deflate de la requête, ne fais rien sinon.
	 * @param communicationEventListener, le communicationEventListener qui effectuera le traitement de la réponse lorsque celle-ci aura été reçue.
	 */
	public MyAsyncTask(String content, String url, String mediaType, Headers headers, boolean enableCompression, CommunicationEventListener communicationEventListener) {
		this.content = content;
		this.url = url;
		this.mediaType = mediaType;
		this.headers = headers;
		this.enableCompression = enableCompression;
		this.communicationEventListener = communicationEventListener;
	}
	
	@Override
	protected String doInBackground(Void... Void) {
		try {
			// on envoit la requête
			Response res = scm.sendRequest(content, url, mediaType, headers, enableCompression);
			if (res.isSuccessful() && res.body() != null) { // la réponse a bien été reçue
				// FIXME: demander comment utiliser le InflaterInputStream
				if (enableCompression) { // la compression est activée -> La réponse est compressée -> il faut donc décompresser la réponse (son body)
					byte[] body = res.body().bytes();
					Inflater inflater = new Inflater(true);
					inflater.setInput(body);
					ByteArrayOutputStream stream = new ByteArrayOutputStream(body.length);
					
					byte[] buffer = new byte[body.length];
					while (!inflater.finished()) {
						int count = inflater.inflate(buffer);
						stream.write(buffer, 0, count);
					}
					stream.close();
					return stream.toString(); // on retourne le body de la requête décompressé
				} else { // la compression n'est pas activée -> la réponse n'est pas compressée
				return res.body().string();
				}
			} else { // la réponse n'est pas valide
				throw new IOException("Unexpected code " + res);
			}
		} catch (IOException | DataFormatException e) {
			e.printStackTrace();
			// on enregistre l'erreur survenue
			error = e;
			// Une erreur s'est produite, on annule la tâche asynchrone.
			this.cancel(false);
		}
		// FIXME: Que faut-il retourner en cas d'erreur?
		return null; // une erreur s'est produite
	}
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
		// on affiche l'erreur survenue durant le traitement de la tâche asynchrone
		communicationEventListener.handleServerResponse(error.getMessage());
	}
	
	@Override
	protected void onPostExecute(String response) {
		super.onPostExecute(response);
		communicationEventListener.handleServerResponse(response);
	}
}
