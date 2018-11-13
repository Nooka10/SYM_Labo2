package SymComManager;

import android.os.AsyncTask;
import okhttp3.Headers;
import okhttp3.Response;

import java.io.IOException;

public class MyAsyncTask extends AsyncTask<Void, String, String> {
	private String content;
	private String url;
	private String mediaType;
	private Headers headers;
	private SymComManager scm = SymComManager.getInstance();
	private CommunicationEventListener communicationEventListener;
	
	
	public MyAsyncTask(String content, String url, String mediaType, Headers headers, CommunicationEventListener communicationEventListener) {
		this.content = content;
		this.url = url;
		this.mediaType = mediaType;
		this.headers = headers;
		this.communicationEventListener = communicationEventListener;
	}
	
	@Override
	protected String doInBackground(Void... Void) {
		try {
			Response res = scm.sendRequest(content, url, mediaType, headers);
			if(res.isSuccessful())
				return res.body().string();
			else
				throw new IOException("Unexpected code " + res);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String response) {
		super.onPostExecute(response);
		
		communicationEventListener.handleServerResponse(response);
	}
}
