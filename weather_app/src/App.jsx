import React, { useEffect, useState } from 'react';
import WeatherSummary from './Components/WeatherSummary/WeatherSummary';
import WeatherFetcher from './Components/WeatherFetcher/WeatherFetcher';
import AlertChecker from './Components/AlertChecker/AlertChecker';
import './App.css'; 

const cities = [
  { name: 'Delhi', id: '1273294' },
  { name: 'Mumbai', id: '1275339' },
  { name: 'Chennai', id: '1264527' },
  { name: 'Bangalore', id: '1277333' },
  { name: 'Kolkata', id: '1275004' },
  { name: 'Hyderabad', id: '1269843' },
];

const App = () => {
  const [weatherData, setWeatherData] = useState([]);
  const [alertMessage, setAlertMessage] = useState('');
  const [alertVisible, setAlertVisible] = useState(false); 
  const [dailySummary, setDailySummary] = useState({});

  const handleWeatherData = (data) => {
    setWeatherData(data);
  };

  const handleAlert = (message) => {
    setAlertMessage(message);
    setAlertVisible(true); 
  };

  const closeAlert = () => {
    setAlertVisible(false); 
  };

  useEffect(() => {
    if (weatherData.length) {
      const summary = WeatherSummary(cities, weatherData);
      setDailySummary(summary);
    }
  }, [weatherData]);

  return (
    <div>
      <h1>Real-time Weather Monitoring</h1>
      <WeatherFetcher onDataFetched={handleWeatherData} cities={cities} />
      <AlertChecker weatherData={weatherData} onAlert={handleAlert} />

      {alertVisible && (
        <div className="alert-message">
          {alertMessage}
          <button className="close-btn" onClick={closeAlert}>&times;</button>
        </div>
      )}

      <div className="container">
        <div className="weather-boxes">
          {weatherData.map((data, index) => (
            <div key={index} className="weather-box">
              <h3>{data.city}</h3>
              <p>Temperature: {data.temp.toFixed(2)}°C</p>
              <p>Feels like: {data.feels_like.toFixed(2)}°C</p>
              <p>Humidity: {data.humidity}%</p> 
              <p>Wind Speed: {data.wind_speed} m/s</p> 
              <p>Weather: {data.main}</p>
              <p>Time: {data.time}</p>
            </div>
          ))}
        </div>
      </div>

      <h2>Daily Summary</h2>
      <div className="daily-summary-container">
        {Object.keys(dailySummary).map(city => (
          <div key={city} className="daily-summary-box">
            <h3>{city}</h3>
            <p>Average Temperature: {dailySummary[city].avgTemp}°C</p>
            <p>Max Temperature: {dailySummary[city].maxTemp}°C</p>
            <p>Min Temperature: {dailySummary[city].minTemp}°C</p>
            <p>Average Humidity: {dailySummary[city].avgHumidity}%</p> 
            <p>Average Wind Speed: {dailySummary[city].avgWindSpeed} m/s</p> 
            <p>Dominant Condition: {dailySummary[city].dominantCondition}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default App;
