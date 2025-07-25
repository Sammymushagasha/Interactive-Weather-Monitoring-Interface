package com.coder.finalweatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Weather App");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //Starting application
        launch();
    }

    public JSONObject getWeatherData(String locationName) {
        JSONArray location = getLocationData(locationName);

        JSONObject lo = (JSONObject) location.get(0);
        double lat = (double) lo.get("latitude");
        double lon = (double) lo.get("longitude");

        String url = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + lat + "&longitude=" + lon +
                "&hourly=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m&timezone=America%2FNew_York";

        try {
            HttpURLConnection con = fetchApiResponse(url);
            if (con.getResponseCode() != 200) {
                System.out.println("Error: Cannot make connection to API");
                return null;
            }
            StringBuilder result = new StringBuilder();
            Scanner scanner = new Scanner(con.getInputStream());
            while (scanner.hasNext()) {
                result.append(scanner.nextLine());
            }

            scanner.close();
            con.disconnect();

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(result));

            JSONObject hourly = (JSONObject) jsonObject.get("hourly");

            JSONArray time = (JSONArray) hourly.get("time");
            int index = findIndexOfCurrentTime(time);

            JSONArray temp = (JSONArray) hourly.get("temperature_2m");

            double temperature = (double) temp.get(index);

            JSONArray weathercode = (JSONArray) hourly.get("weather_code");
            String weatherCondition = convertWeatherCode((long) weathercode.get(index));

            JSONArray relHumidity = (JSONArray) hourly.get("relative_humidity_2m");
            long humidity = (long) relHumidity.get(index);

            JSONArray windSpeedData = (JSONArray) hourly.get("wind_speed_10m");
            double windSpeed = (double) windSpeedData.get(index);

            JSONObject weatherData = new JSONObject();
            weatherData.put("temperature", temperature);
            weatherData.put("weather_condition", weatherCondition);
            weatherData.put("humidity", humidity);
            weatherData.put("wind_speed", windSpeed);

            return weatherData;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public JSONArray getLocationData(String locationName) {
        locationName = locationName.replaceAll(" ", "+");

        String url = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                locationName + "&count=10&language=en&format=json";

        try {
            HttpURLConnection connection = fetchApiResponse(url);

            if (connection.getResponseCode() != 200) {
                System.out.println("Error: Cannot make connection to API");
                return null;
            } else {
                StringBuilder result = new StringBuilder();
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNext()) {
                    result.append(scanner.nextLine());
                }

                scanner.close();
                connection.disconnect();

                JSONParser parser = new JSONParser();
                JSONObject resultJsonObject = (JSONObject) parser.parse(String.valueOf(result));

                JSONArray locationData = (JSONArray) resultJsonObject.get("results");
                return locationData;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpURLConnection fetchApiResponse(String url) {
        try {
            URL newURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) newURL.openConnection();

            connection.setRequestMethod("GET");

            connection.connect();
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int findIndexOfCurrentTime(JSONArray time) {
        String currentTime = getCurrentTime();
        for (int i = 0; i < time.size(); i++) {
            String t = (String) time.get(i);
            if (t.equalsIgnoreCase(currentTime)) {
                return i;
            }
        }
        return 0;
    }

    public String getCurrentTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");

        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
    }

    public String convertWeatherCode(Long weatherCode) {
        String weatherCondition = "";
        if (weatherCode == 0L) {
            weatherCondition = "Clear";
        } else if (weatherCode <= 3L && weatherCode > 0) {
            weatherCondition = "Cloudy";
        } else if ((weatherCode >= 51L && weatherCode <= 67L) || (weatherCode >= 80 && weatherCode <= 99L)) {
            weatherCondition = "Rain";
        } else if (weatherCode >= 71L && weatherCode <= 77L) {
            weatherCondition = "Snow";
        }

        return weatherCondition;
    }

}
