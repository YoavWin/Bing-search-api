package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bing.api.BingWebSearch;
import bing.api.UrlSearch;
import database.MongoInteractor;
import database.UrlEntity;

@WebServlet("/ProcessQueryServlet")
public class ProcessQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String name;
	private String url;
	private MongoInteractor mongoInteractor = MongoInteractor.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		name = request.getParameter("name");
		

		try {
			BingWebSearch urlSearch = new UrlSearch();
			urlSearch.webSearch(name);
			url = urlSearch.getQueryResult();
			mongoInteractor.write(new UrlEntity(name, url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response.sendRedirect("/index.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
