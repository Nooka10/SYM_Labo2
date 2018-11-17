package SymComManager;

import android.os.AsyncTask;
import okhttp3.Headers;
import okhttp3.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class MyAsyncTask extends AsyncTask<Void, String, String> {
	private String content;
	private String url;
	private String mediaType;
	private Headers headers;
	private boolean enableCompression;
	private SymComManager scm = SymComManager.getInstance();
	private CommunicationEventListener communicationEventListener;
	
	
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
			Response res = scm.sendRequest(content, url, mediaType, headers, enableCompression);
			if (res.isSuccessful()) {
				
				// FIXME: demander comment utiliser le InflaterInputStream
				if (enableCompression) {
					// Decompress the bytes
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
					
					return stream.toString();
				} else {
				return res.body().string();
				}
			} else {
				throw new IOException("Unexpected code " + res);
			}
		} catch (IOException | DataFormatException e) {
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
