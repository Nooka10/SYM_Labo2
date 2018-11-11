package SymComManager;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DelayedSymComManager extends AsyncSymComManager {
	private SymComManager scm = SymComManager.getInstance();
	
	private ArrayList<Pair<String, String>> waitingRequests = new ArrayList<>();
	boolean isInWaitingQueue = false;
	boolean hasAScheduler = false;
	
	Activity activity = null;
	
	public DelayedSymComManager(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public void sendRequest(String content, String url) throws Exception {
		
		if (isNetworkAvailable()) {
			// La connection à internet est disponible -> on envoit la requête
			super.sendRequest(content, url);
		} else {
			// la connection internet n'est pas disponible -> on ajoute la requête dans la file d'attente si elle ne s'y trouve pas déjà
			if (!isInWaitingQueue) {
				waitingRequests.add(new Pair<>(content, url));
				
				isInWaitingQueue = true;
				
				int initialDelay = 10;
				int delay = 30;
				
				// si la requête n'a pas encore de scheduler, on le crée, sinon, on ne fait rien.
				if (!hasAScheduler) {
					hasAScheduler = true;
					
					// on crée un ScheduledExecutorService qui retentera d'envoyer la requête après 'initialDelay' puis toutes les 'delay' secondes à
					// l'aide d'un nouveau thread.
					// https://developer.android.com/reference/java/util/concurrent/ScheduledExecutorService
					ScheduledExecutorService  scheduler = Executors.newSingleThreadScheduledExecutor();
					
					scheduler.scheduleAtFixedRate(new Runnable() {
						public void run() {
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
						}
					}, initialDelay, delay, TimeUnit.SECONDS);
				}
			}
		}
	}
	
	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
