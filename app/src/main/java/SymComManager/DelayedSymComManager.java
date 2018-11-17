package SymComManager;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.util.Pair;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class DelayedSymComManager extends AsyncSymComManager {
	private ArrayList<Pair<String, String>> waitingRequests = new ArrayList<>();
	boolean isInWaitingQueue = false;
	
	Activity activity;
	
	public DelayedSymComManager(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public synchronized void sendRequest(String content, String url) throws Exception {
		if (isNetworkAvailable()) {
			// La connection à internet est disponible -> on envoit la requête
			super.sendRequest(content, url);
		} else {
			// la connection internet n'est pas disponible -> on ajoute la requête dans la file d'attente si elle ne s'y trouve pas déjà
			if (!isInWaitingQueue) {
				waitingRequests.add(new Pair<>(content, url));
				isInWaitingQueue = true;
				
				Thread scheduler = new Thread(new Runnable() {
					@Override
						public void run() {
						while (true) {
							// tant que la connection internet est disponible et qu'il y a des requêtes en attente d'envoi, on les envoie une à une.
							while (isNetworkAvailable() && !waitingRequests.isEmpty()) {
								// on récupère la 1ère requête de la liste d'attente et on l'enlève de la liste d'attente
								Pair<String, String> request = waitingRequests.remove(0);
								isInWaitingQueue = false;
								try {
									sendRequest(request.first, request.second);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							try {
								sleep(10000);
							} catch (InterruptedException e) {
							}
						}
					}
				});
				scheduler.start();
			}
		}
	}
	
	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
