package SymComManager;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.util.Pair;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Classe gérant la communication avec le serveur pour le fragment DelayedSendFragment.
 * Hérite de la classe AsyncSymComManager.
 */
public class DelayedSymComManager extends AsyncSymComManager {
	private final ArrayList<Pair<String, String>> waitingRequests = new ArrayList<>();
	private boolean isInWaitingQueue = false;
	
	private final Activity activity;
	
	/**
	 * Constructeur.
	 *
	 * @param activity,
	 * 		l'activité appelant le constructeur.
	 */
	public DelayedSymComManager(Activity activity) {
		this.activity = activity;
		
		// on crée un thread qui tournera en boucle et traitera toutes les requêtes en attente lorsque la connexion à internet est disponible
		Thread scheduler = new Thread(new Runnable() {
			@Override
			public void run() {
				// Le thread ne s'arretera jamais...! C'est sans doute la pire des façon de faire, mais ça suffit pour ce labo.
				while (true) {
					// tant que la connection internet est disponible et qu'il y a des requêtes en attente d'envoi, on les envoie une à une.
					while (isNetworkAvailable() && !waitingRequests.isEmpty()) {
						// on récupère la 1ère requête de la liste d'attente et on l'enlève de la liste d'attente
						Pair<String, String> request = waitingRequests.remove(0);
						isInWaitingQueue = false;
						try {
							// on envoit la requête
							sendRequest(request.first, request.second);
						} catch (Exception e) {
							// une erreur est survenue -> on remet la requête dans la liste d'attente
							waitingRequests.add(request);
							e.printStackTrace();
						}
					}
					try {
						// on a traité toutes les requêtes en attente -> on endort le thread pendant 10 secondes.
						sleep(10000);
					} catch (InterruptedException ignored) {
					}
				}
			}
		});
		// on démarre le thread
		scheduler.start();
	}
	
	@Override
	public synchronized void sendRequest(String content, String url) throws Exception {
		if (isNetworkAvailable()) {
			// La connection à internet est disponible -> on envoit la requête
			super.sendRequest(content, url);
		} else {
			// la connection internet n'est pas disponible -> on ajoute la requête dans la file d'attente si elle ne s'y trouve pas déjà
			// la requête sera traitée par le thread qui tourne en boucle lorsque la connexion internet sera rétablie
			if (!isInWaitingQueue) {
				waitingRequests.add(new Pair<>(content, url));
				isInWaitingQueue = true;
			}
		}
	}
	
	/**
	 * Vérifie qu'une connexion a internet soit disponible.
	 * @return true si une connexion à internet est disponible, false sinon.
	 */
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
