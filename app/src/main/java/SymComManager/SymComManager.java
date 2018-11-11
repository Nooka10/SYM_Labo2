package SymComManager;

import okhttp3.*;

import java.util.concurrent.TimeUnit;

class SymComManager {
	private static SymComManager ourInstance = new SymComManager();
	
	static SymComManager getInstance() {
		return ourInstance;
	}
	
	private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
			.connectTimeout(30, TimeUnit.SECONDS)
			.writeTimeout(30, TimeUnit.SECONDS)
			.readTimeout(30, TimeUnit.SECONDS)
			.build();
	
	private SymComManager() {
	}
	
	Request createRequest(String content, String url) {
		// Check URL
		if (url.isEmpty()) {
			throw new IllegalArgumentException("URL cannot be empty");
		}
		
		RequestBody requestBody = RequestBody.create(
				MediaType.parse("text/plain; charset=utf-8"),
				content
		);
		return new Request.Builder()
				.url(url)
				.post(requestBody)
				.addHeader("content-type", "plain/text")
				.build();
	}
	
	OkHttpClient getOkHttpClient() {
		return okHttpClient;
	}
	
	void sendRequest(String content, String url, Callback callback) {
		Request request = createRequest(content, url);
		
		okHttpClient.newCall(request).enqueue(callback);
	}
}
