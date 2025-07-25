# Interactive Weather Monitoring Interface ☁️🌡️

A desktop weather app built with **JavaFX** that fetches and displays real-time weather data for any city using the **Open-Meteo API**. The app shows temperature, humidity, wind speed, and weather conditions with dynamic icons.

---

## 🧩 Features

- Fetches current weather data for any user-entered city
- Displays temperature (in Fahrenheit), humidity, wind speed, and sky condition
- Dynamic weather icons based on conditions: Clear, Cloudy, Rain, Snow
- User-friendly JavaFX interface with input field and labels
- Uses Open-Meteo’s free API for location and weather data

---

## 🛠️ Technologies Used

- Java 17+ (or compatible version)
- JavaFX
- JSON Simple (for parsing API responses)
- Open-Meteo API

---

## 🚀 How It Works

1. User enters a city name in the input field.
2. The app queries the Open-Meteo Geocoding API to find latitude and longitude of the city.
3. Using the coordinates, the app requests weather forecast data from Open-Meteo.
4. Current hour’s weather data (temperature, humidity, wind speed, weather code) is extracted.
5. Weather condition codes are converted to human-readable forms and corresponding icons are displayed.
6. Weather info is shown in the UI labels.

---

## 📦 Installation & Running

### Requirements

- Java JDK installed (17 or above recommended)
- JavaFX SDK installed and configured
- JSON Simple library added to your project dependencies

### Steps

1. Clone this repository:
   ```bash
   git clone https://github.com/Sammymushagasha/Interactive-Weather-Monitoring-Interface.git
