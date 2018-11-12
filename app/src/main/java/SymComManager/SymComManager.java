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
	
	private Request createRequest(String content, String url, String applicationType, Headers headers) {
		// Check URL
		if (url.isEmpty()) {
			throw new IllegalArgumentException("URL cannot be empty");
		}
		
		RequestBody requestBody = RequestBody.create(
				MediaType.parse(applicationType),
				content
		);
		return new Request.Builder()
				.url(url)
				.post(requestBody)
				.headers(headers)
				// FIXME: j'arrive pas à configurer ces entêtes.... À quoi servent-elle et sont-elles obligatoires?
				// .addHeader("X-Network", "[CSD, GPRS, EDGE, UMTS, HSPA, LTE]")
				// .addHeader("X-Content-Encoding", "deflate")
				.build();
	}
	
	OkHttpClient getOkHttpClient() {
		return okHttpClient;
	}
	
	void sendRequest(String content, String url, String mediaType, Headers headers, Callback callback) {
		Request request = createRequest(content, url, mediaType, headers);
		
		okHttpClient.newCall(request).enqueue(callback);
	}
}
