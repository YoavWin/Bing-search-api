package bing.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import resources.Config;

public class UrlSearch extends BingSearch {

	private static String bingApiFailedQueryAnswer;

	public UrlSearch() {
		bingApiFailedQueryAnswer = Config.getInstance().getProperty("bingApiFailedQueryAnswer");
	}

	@Override
	public String getQueryResult() {
		JsonObject webPages = null;
		JsonArray values = null;
		JsonObject page = null;
		String url = null;
		if (result != null) {
			webPages = result.getAsJsonObject("webPages");
			if (webPages != null) {
				values = webPages.getAsJsonArray("value");
				if (values != null && values.size() > 0) {
					page = values.get(0).getAsJsonObject();
					if (page != null) {
						url = page.get("url").getAsString();
						if (checkValidQueryResult(url))
							return url;
					}
				}
			}
		}
		return bingApiFailedQueryAnswer;
	}

	public boolean checkValidQueryResult(String urlResult) {
		return urlResult.contains(bingApiAdditionalSearchString);
	}
}
