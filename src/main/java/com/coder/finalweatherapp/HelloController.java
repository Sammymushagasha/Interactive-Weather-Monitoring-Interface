package com.coder.finalweatherapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.json.simple.JSONObject;

public class HelloController {

    public Image img;

    public ImageView view;

    @FXML
    public TextField cityEntered =  new TextField("");

    @FXML
    public Label iconLabel;

    @FXML
    public Label skyDescription = new Label("");

    public JSONObject weatherData = new JSONObject();

    public HelloApplication hA = new HelloApplication();
    @FXML
    private Label tempOutput;

    @FXML
    private Label wsOutput;

    @FXML
    private Label humOutput;

    @FXML
    void onHelloButtonClick(ActionEvent event) {

        String userInput = cityEntered.getText();
        if(userInput.replaceAll("\\s", "").length() <= 0) {
            System.exit(0);
        }

        weatherData = hA.getWeatherData(userInput);

        if (weatherData == null) {
            System.out.println("Could not retrieve weather data. Please check the city name.");
            return;
        }

        String weatherCondition = (String) weatherData.get("weather_condition");

        switch (weatherCondition){
            case "Clear":
                img = new Image("C:\\Java\\finalWeatherApp\\src\\Icons\\clear.png");
                view = new ImageView(img);
                iconLabel.setGraphic(view);
                skyDescription.setText("Clear");
                break;
            case "Cloudy":
                img = new Image("C:\\Java\\finalWeatherApp\\src\\Icons\\cloudy.png");
                view = new ImageView(img);
                iconLabel.setGraphic(view);
                skyDescription.setText("Cloudy");
                break;
            case "Rain":
                img = new Image("C:\\Java\\finalWeatherApp\\src\\Icons\\rain.png");
                view = new ImageView(img);
                iconLabel.setGraphic(view);
                skyDescription.setText("Rain");
                break;
            case "Snow":
                img = new Image("C:\\Java\\finalWeatherApp\\src\\Icons\\snow.png");
                view = new ImageView(img);
                iconLabel.setGraphic(view);
                skyDescription.setText("Snow");
                break;
        }

        double tempinCels = (double) weatherData.get("temperature");
        double tempInFaren = Math.round(tempinCels * (9/5) + 32) + 18;

        tempOutput.setText(tempInFaren + "°F");

        double windspeed = (double) weatherData.get("wind_speed");
        double wsReturn = Math.round(windspeed);
        wsOutput.setText(wsReturn + "km/h");

        long humidity = Math.round((long) weatherData.get("humidity")) - 5;
        humOutput.setText(humidity + "%");



    }

}