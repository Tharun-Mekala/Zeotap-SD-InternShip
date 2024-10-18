export const convertToCelsius = (kelvin) => {
    return kelvin - 273.15;
  };
  
  export const findDominantCondition = (data) => {
    const conditionCount = data.reduce((acc, item) => {
      acc[item.main] = (acc[item.main] || 0) + 1;
      return acc;
    }, {});
    return Object.keys(conditionCount).reduce((a, b) => (conditionCount[a] > conditionCount[b] ? a : b));
  };
  