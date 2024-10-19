
import React, { useEffect } from 'react';
import { convertToCelsius } from '../helpers/helpers';



const API_KEY = "Your API KEY"; // Replace with your API key
const WEATHER_URL = 'https://api.openweathermap.org/data/2.5/weather';

const WeatherFetcher = ({ onDataFetched, cities }) => {
  const fetchWeatherData = () => {
    const promises = cities.map(city =>
      fetch(`${WEATHER_URL}?id=${city.id}&APPID=${API_KEY}`)
        .then(response => response.json())
        .then(data => ({
          city: city.name,
          main: data.weather[0].main,
          temp: convertToCelsius(data.main.temp),
          feels_like: convertToCelsius(data.main.feels_like),
          time: new Date(data.dt * 1000).toLocaleTimeString(),
          humidity: data.main.humidity,
          wind_speed: data.wind.speed,
        }))
    );

    Promise.all(promises)
      .then(results => onDataFetched(results))
      .catch(error => console.error('Error fetching data:', error));
  };

  useEffect(() => {
    fetchWeatherData();
    const intervalId = setInterval(fetchWeatherData, 300000);
    return () => clearInterval(intervalId);
  }, []);

  return null; // No UI to render, just fetching data
};

export default WeatherFetcher;
