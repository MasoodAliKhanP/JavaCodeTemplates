package com.masood;

import java.io.IOException;
import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import com.google.gson.Gson;

public class HttpClientExample {

	// one instance, reuse
	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	public static void main(String[] args) throws Exception {

		HttpClientExample obj = new HttpClientExample();

		try {
//			System.out.println("Testing 1 - Send Http GET request");
//			obj.sendGet();

//			System.out.println("Test 1 - Get auth code");
//			String authCode = obj.getAuthCode();

			System.out.println("Test - Get Player");
			for (int i = 1; i < 50; i++) {
				obj.getPlayer();
			}

		} finally {
			obj.close();
		}
	}

	private void close() throws IOException {
		httpClient.close();
	}

	private void sendGet() throws Exception {

		HttpGet request = new HttpGet("https://www.google.com/search?q=mkyong");

		// add request headers
		request.addHeader("custom-key", "mkyong");
		request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

		try (CloseableHttpResponse response = httpClient.execute(request)) {

			// Get HttpResponse Status
			System.out.println(response.getStatusLine().toString());

			HttpEntity entity = response.getEntity();
			Header headers = entity.getContentType();
			System.out.println(headers);

			if (entity != null) {
				// return it as a String
				String result = EntityUtils.toString(entity);
				System.out.println(result);
			}

		}

	}

	private void sendPost() throws Exception {

		HttpPost post = new HttpPost("https://httpbin.org/post");

		// add request parameter, form parameters
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("username", "abc"));
		urlParameters.add(new BasicNameValuePair("password", "123"));
		urlParameters.add(new BasicNameValuePair("custom", "secret"));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			System.out.println(EntityUtils.toString(response.getEntity()));
		}

	}

	private String getAuthCode() throws Exception {

		HttpPost post = new HttpPost("https://apiback.secure-backend.com/gateway-inbound-1.0/v1/oauth/authorize");

		ClientDetails cDetails = new ClientDetails("21dukes_chat", "Ja3AhpsV0jn4toT");
		Authorization auth = new Authorization("CLIENT_CREDENTIALS", cDetails);

		Gson gson = new Gson();
		StringEntity postingString = new StringEntity(gson.toJson(auth));// gson.tojson() converts your pojo to json

		System.out.println(postingString);
		post.setEntity(postingString);

		post.setHeader("Accept", "application/json");
		post.setHeader("Content-type", "application/json");

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {
			String json = EntityUtils.toString(response.getEntity());
			System.out.println(json);
			AuthResponse authRes = gson.fromJson(json, AuthResponse.class);
			return authRes.getAuthCode();
		}
	}

//	@Retryable(maxAttempts = 5, value = RuntimeException.class, backoff = @Backoff(delay = 2000, multiplier = 2))
//	@Retryable(value = {UnknownHostException.class}, maxAttempts = 3)
	private void getPlayer() throws Exception {

		HttpPost post = new HttpPost("https://apiback.secure-backend.com/gateway-inbound-1.0/v1/chat/GetPlayer");

		PlayerRequest req = new PlayerRequest("21dukes_chat", "21dukes_chat", "683181600667014");

		Gson gson = new Gson();
		StringEntity postingString = new StringEntity(gson.toJson(req));// gson.tojson() converts your pojo to json

		System.out.println(postingString);
		post.setEntity(postingString);

		post.setHeader("Accept", "application/json");
		post.setHeader("Content-type", "application/json");
		post.setHeader("accesstoken", "b02090c0af28d2f60a439ba91ff3bf48");

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(post);
		String json = EntityUtils.toString(response.getEntity());
		System.out.println(json);
	}

//	@Recover
//	public void recover(RuntimeException e) {
//		System.out.println("*(***** recovered *****");
//	}

}