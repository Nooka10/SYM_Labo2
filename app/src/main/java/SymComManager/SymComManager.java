package SymComManager;

import okhttp3.*;

import java.io.IOException;
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
	
	private SymComManager() { }
	
	private Request createRequest(String content, String url, String applicationType, Headers headers) {
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
				.build();
	}
	
	Response sendRequest(String content, String url, String mediaType, Headers headers) throws IOException {
		Request request = createRequest(content, url, mediaType, headers);
		
		return okHttpClient.newCall(request).execute();
	}
}
