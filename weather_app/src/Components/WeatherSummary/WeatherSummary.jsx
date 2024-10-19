import { findDominantCondition } from "../helpers/helpers";

const WeatherSummary = (cities, weatherData) => {
  const summary = cities.reduce((acc, city) => {
    // Filter weather data for the current city
    const cityData = weatherData.filter(item => item.city === city.name);

    if (cityData.length === 0) return acc; // Skip if no data for the city

    const temps = cityData.map(item => item.temp);
    const humidities = cityData.map(item => item.humidity);
    const windSpeeds = cityData.map(item => item.wind_speed);

    // Calculate average temperature, max temperature, and min temperature
    acc[city.name] = {
      avgTemp: (temps.reduce((sum, t) => sum + t, 0) / temps.length).toFixed(2),
      maxTemp: Math.max(...temps).toFixed(2),
      minTemp: Math.min(...temps).toFixed(2),
      avgHumidity: (humidities.reduce((sum, h) => sum + h, 0) / humidities.length).toFixed(2),
      avgWindSpeed: (windSpeeds.reduce((sum, w) => sum + w, 0) / windSpeeds.length).toFixed(2),
      dominantCondition: findDominantCondition(cityData),
    };

    return acc;
  }, {});

  return summary; // Return the weather summary for all cities
};

export default WeatherSummary;
