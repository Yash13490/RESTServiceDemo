package com.myrest.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


public class ApacheHttpClientPost {

	// http://localhost:8080/RESTfulExample/json/product/post

	public static void main(String[] args) {
		
		//	new ApacheHttpClientPost().callLocalApiPost();
			
			new ApacheHttpClientPost().marketPlacePostClient();

	}
	
	public void callLocalApiPost() {
		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(
					"http://localhost:8080/RESTfulExample/json/product/post");

			StringEntity input = new StringEntity(
					"{\"qty\":100,\"name\":\"iPad 4\"}");
			input.setContentType("application/json");
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {

				System.out.println(output);
			}

			httpClient.getConnectionManager().shutdown();

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public void marketPlacePostClient() {
		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost("http://apimpiat.gslb.es.oneadp.com/events/core/v1/consumer-application-subscription-credentials.read");

			new ApacheHttpClientPost().getHeadersToGetConsumerCredetials(postRequest);

			StringEntity input = new StringEntity("{ \"events\": [ { \"serviceCategoryCode\": { \"codeValue\": \"core\" }, \"eventNameCode\": { \"codeValue\": \"consumer-application-subscription-credentials.read\" } } ] } ");
			input.setContentType("application/json");
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			System.out.println("Response code::" + response.getStatusLine().getStatusCode());

			InputStream in = response.getEntity().getContent();
			String responseString = convertStreamToString(in);

			System.out.println("responseString" + responseString);

			httpClient.getConnectionManager().shutdown();

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public HttpPost getHeadersToGetConsumerCredetials(HttpPost post){

		String methodName = "getHeadersToGetAccessToken";

		post.setHeader("associateoid", "G4W97GKEMRCM7MRT");
		post.setHeader("orgoid", "G4PEZCVKF64G4YHR");
		post.setHeader("realm", "iSI");
		post.setHeader("consumerappoid", "TCS");
		post.setHeader("Content-Type","application/json");
		post.setHeader("Host","apimpiat.gslb.es.oneadp.com");

		return post;
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}