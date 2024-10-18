import React, { useEffect } from 'react';

const AlertChecker = ({ weatherData, onAlert }) => {
  useEffect(() => {
    weatherData.forEach(data => {
      if (data.temp > 35) {
        onAlert(`Alert! High temperature in ${data.city}: ${data.temp.toFixed(2)}°C`);
      }
    });
  }, [weatherData]);

  return null; 
};

export default AlertChecker;
