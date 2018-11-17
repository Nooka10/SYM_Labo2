package SymComManager;

import okhttp3.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 *
 */
class SymComManager {
	private static final SymComManager ourInstance = new SymComManager();
	
	static SymComManager getInstance() {
		return ourInstance;
	}
	
	private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
			.connectTimeout(30, TimeUnit.SECONDS)
			.writeTimeout(30, TimeUnit.SECONDS)
			.readTimeout(30, TimeUnit.SECONDS)
			.build();
	
	private SymComManager() { }
	
	private Request createRequest(String content, String url, String applicationType, Headers headers, boolean enableCompression) throws IOException {
		if (url.isEmpty()) {
			throw new IllegalArgumentException("URL cannot be empty");
		}
		
		if (enableCompression) {
			try {
				RequestBody requestBody = RequestBody.create(
						MediaType.parse(applicationType),
						compressData(content)
				);
				
				return new Request.Builder()
						.url(url)
						.post(requestBody)
						.headers(headers)
						.addHeader("X-Content-Encoding", "deflate")
						.build();
			} catch (IOException e) {
				e.printStackTrace();
				throw new IOException("An error occuprs durring the compression of the content");
			}
			
		} else {
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
	}
	
	private byte[] compressData(String content) throws IOException {
		Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION, true);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, deflater);
		deflaterOutputStream.write(content.getBytes());
		deflaterOutputStream.finish();
		deflaterOutputStream.close();
		
		return byteArrayOutputStream.toByteArray();
	}
	
	Response sendRequest(String content, String url, String mediaType, Headers headers, boolean enableCompression) throws IOException {
		Request request = createRequest(content, url, mediaType, headers, enableCompression);
		
		return okHttpClient.newCall(request).execute();
	}
}
