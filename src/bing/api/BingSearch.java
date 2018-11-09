package bing.api;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import resources.Config;

public abstract class BingSearch implements BingWebSearch {

	private static String bingApiSubcriptionKey;
	private static String bingApiHost;
	private static String bingApiPath;
	private static String bingApiNumOfReturningResult;
	protected static String bingApiAdditionalSearchString;
	protected JsonObject result;

	public BingSearch() {
		bingApiSubcriptionKey = Config.getInstance().getProperty("bingApiSubcriptionKey");
		bingApiHost = Config.getInstance().getProperty("bingApiHost");
		bingApiPath = Config.getInstance().getProperty("bingApiPath");
		bingApiNumOfReturningResult = Config.getInstance().getProperty("bingApiNumOfReturningResult");
		bingApiAdditionalSearchString = Config.getInstance().getProperty("bingApiAdditionalSearchString");
	}

	public void webSearch(String searchQuery) throws Exception {

		URL url = new URL(bingApiHost + bingApiPath + "?q="
				+ URLEncoder.encode(searchQuery + " " + bingApiAdditionalSearchString, "UTF-8") + "&count="
				+ bingApiNumOfReturningResult);

		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestProperty("Ocp-Apim-Subscription-Key", bingApiSubcriptionKey);

		try (Scanner scanner = new Scanner(connection.getInputStream())) {
			String response = scanner.useDelimiter("\\A").next();
			result = (JsonObject) new JsonParser().parse(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
