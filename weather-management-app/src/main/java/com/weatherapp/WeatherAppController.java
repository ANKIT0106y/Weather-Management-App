package com.weatherapp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/weather")
public class WeatherAppController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String API_KEY = "0204f8004f6fdfc05af1c9743b8ac721";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String city = request.getParameter("city");
        if (city == null || city.isEmpty()) {
            response.getWriter().println("City name is required!");
            return;
        }

        String apiUrl = String.format(BASE_URL, city, API_KEY);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(apiUrl);
            try (CloseableHttpResponse httpResponse = client.execute(httpGet)) {
                String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
                JsonNode rootNode = new ObjectMapper().readTree(jsonResponse);

                String weather = rootNode.path("weather").get(0).path("description").asText();
                double temp = rootNode.path("main").path("temp").asDouble();

                request.setAttribute("city", city);
                request.setAttribute("weather", weather);
                request.setAttribute("temperature", temp);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (Exception e) {
            response.getWriter().println("Error fetching weather data: " + e.getMessage());
        }
    }
}