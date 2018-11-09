package bing.api;

public interface BingWebSearch {
	public void webSearch(String searchQuery) throws Exception;

	public String getQueryResult();
}
